package com.example.client

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.client.data.local.db.AppDatabase
import com.example.client.data.repository.CarRepository
import com.example.client.datastore.SessionManager
import com.example.client.model.Car
import com.example.client.network.RetrofitClient
import com.example.client.ui.theme.ClientTheme
import com.example.client.utils.ConnectionState
import com.example.client.utils.formatDate
import com.example.client.viewmodel.*
import com.example.client.worker.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel()
        askNotificationPermission()
        setContent {
            ClientTheme {
                val context = LocalContext.current
                val database by lazy { AppDatabase.getDatabase(context) }
                val sessionManager by lazy { SessionManager(context) }
                val carRepository by lazy { CarRepository(RetrofitClient.instance, database.carDao(), database.pendingUpdateDao(), sessionManager) }
                var userId by remember { mutableStateOf<String?>(null) }

                if (userId != null) {
                    val factory = MainViewModelFactory(carRepository, userId!!)
                    MainScreen(factory = factory) {
                        car, updatedData -> sendNotification(car)
                    }
                    scheduleSync(context)
                } else {
                    LoginScreen(onLoginSuccess = { newUserId -> userId = newUserId })
                }
            }
        }
    }

    private fun scheduleSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Car Updates"
            val descriptionText = "Notifications for car updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CAR_UPDATES", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(car: Car) {
        val builder = NotificationCompat.Builder(this, "CAR_UPDATES")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Car Updated")
            .setContentText("Car ${car.name} has been updated.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(car.id.hashCode(), builder.build())
    }
}

@Composable
fun SimpleHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit, loginViewModel: LoginViewModel = viewModel()) {
    val loginState by loginViewModel.loginState.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SimpleHeader("Login")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { loginViewModel.login(username, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = loginState != LoginState.Loading
                ) {
                    Text("Login")
                }

                when (val state = loginState) {
                    is LoginState.Loading -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                    is LoginState.Error -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    }
                    is LoginState.Success -> {
                        onLoginSuccess(state.userId)
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun MainScreen(factory: MainViewModelFactory, sendNotification: (Car, Map<String, Any>) -> Unit) {
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val itemsState by mainViewModel.itemsState.collectAsState()
    val selectedCar by mainViewModel.selectedCar.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val connectivityViewModel: ConnectivityViewModel = viewModel(factory = ConnectivityViewModelFactory(context))
    val connectionState by connectivityViewModel.connectionState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val wasOffline = remember { mutableStateOf(connectionState is ConnectionState.Unavailable) }

    LaunchedEffect(connectionState) {
        val isOffline = connectionState is ConnectionState.Unavailable
        if (wasOffline.value && !isOffline) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
        }
        wasOffline.value = isOffline
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleHeader("Cars")

        if (connectionState is ConnectionState.Unavailable) {
            Snackbar(modifier = Modifier.padding(16.dp)) {
                Text(text = "You are offline. Some functionality may not be available.")
            }
        } else {
            Snackbar(modifier = Modifier.padding(16.dp)) {
                Text(text = "You are online.")
            }
        }

        LaunchedEffect(key1 = Unit) {
            mainViewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.CarUpdated -> {
                        showEditDialog = false
                    }

                    is UiEvent.CarDeselected -> {
                        showEditDialog = false
                    }

                    else -> {}
                }
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            // Master list
            Box(modifier = Modifier.weight(1f)) {
                when (val state = itemsState) {
                    is ItemsState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is ItemsState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is ItemsState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
                            items(state.items) { item ->
                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) { mainViewModel.selectCar(item) }
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Detail view
            Box(modifier = Modifier.weight(2f)) {
                selectedCar?.let { car ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = car.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Registration Number: ${car.registration_number}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Accident Count: ${car.accident_count}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Registration Date: ${formatDate(car.registration_date)}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { showEditDialog = true }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog) {
        selectedCar?.let { car ->
            EditCarDialog(
                car = car,
                onDismiss = { mainViewModel.deselectCar() },
                onSave = { updatedData ->
                    mainViewModel.updateCar(car, updatedData, context, sendNotification)
                }
            )
        }
    }
}

@Composable
fun EditCarDialog(car: Car, onDismiss: () -> Unit, onSave: (Map<String, Any>) -> Unit) {
    var name by remember { mutableStateOf(car.name) }
    var registrationNumber by remember { mutableStateOf(car.registration_number) }
    var accidentCount by remember { mutableStateOf(car.accident_count.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Car") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = registrationNumber,
                    onValueChange = { registrationNumber = it },
                    label = { Text("Registration Number") }
                )
                OutlinedTextField(
                    value = accidentCount,
                    onValueChange = { accidentCount = it },
                    label = { Text("Accident Count") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedData = mutableMapOf<String, Any>()
                    if (name != car.name) updatedData["name"] = name
                    if (registrationNumber != car.registration_number) updatedData["registration_number"] = registrationNumber
                    if (accidentCount.toIntOrNull() != car.accident_count) updatedData["accident_count"] = accidentCount.toIntOrNull() ?: car.accident_count
                    onSave(updatedData)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
