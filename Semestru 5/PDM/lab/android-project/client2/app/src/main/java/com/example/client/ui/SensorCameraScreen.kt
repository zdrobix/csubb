package com.example.client.ui

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun SensorCameraScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var proximityValue by remember { mutableStateOf(5f) }
    var lightValue by remember { mutableStateOf(0f) }
    var accelX by remember { mutableStateOf(0f) }
    var accelY by remember { mutableStateOf(0f) }
    var volumeLevel by remember { mutableStateOf(0f) }
    
    var hasCameraPermission by remember { mutableStateOf(false) }
    var hasAudioPermission by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasCameraPermission = permissions[Manifest.permission.CAMERA] ?: false
        hasAudioPermission = permissions[Manifest.permission.RECORD_AUDIO] ?: false
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempUri
        }
    }

    // Sound Level Logic
    LaunchedEffect(hasAudioPermission) {
        if (hasAudioPermission) {
            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }
            
            try {
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                recorder.setOutputFile("${context.cacheDir}/temp_audio.3gp")
                recorder.prepare()
                recorder.start()

                while (true) {
                    val amplitude = recorder.maxAmplitude
                    volumeLevel = (amplitude / 32767f)
                    delay(100)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    recorder.stop()
                    recorder.release()
                } catch (e: Exception) {}
            }
        }
    }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_PROXIMITY -> proximityValue = event.values[0]
                    Sensor.TYPE_LIGHT -> lightValue = event.values[0]
                    Sensor.TYPE_ACCELEROMETER -> {
                        accelX = event.values[0]
                        accelY = event.values[1]
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, proximitySensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    val rotationAnimation by animateFloatAsState(
        targetValue = lightValue % 360f,
        animationSpec = tween(durationMillis = 500)
    )
    val ballX by animateFloatAsState(targetValue = -accelX * 10f)
    val ballY by animateFloatAsState(targetValue = accelY * 10f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Advanced Sensors", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        // 1. Proximity Sensor (Animation)
        AnimatedVisibility(
            visible = proximityValue < 5f,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                modifier = Modifier.padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = "Object is Close!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Light Sensor (Rotation)
        Text(text = "Light Level: ${lightValue.toInt()} lux")
        Box(
            modifier = Modifier
                .size(60.dp)
                .rotate(rotationAnimation)
                .background(Color.Yellow)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Accelerometer (Leveler)
        Text(text = "Motion sensor")
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .offset(x = ballX.dp, y = ballY.dp)
                    .size(20.dp)
                    .background(Color.Blue, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 4. Sound Level (Volume Meter)
        Text(text = "Ambient Volume")
        LinearProgressIndicator(
            progress = volumeLevel,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(CircleShape),
            color = if (volumeLevel > 0.7f) Color.Red else Color.Green
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            if (!hasCameraPermission || !hasAudioPermission) {
                permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
            } else {
                val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
                tempUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                cameraLauncher.launch(tempUri!!)
            }
        }) {
            Text("Grant Permissions & Take Photo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Captured Image",
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back to Cars")
        }
    }
}
