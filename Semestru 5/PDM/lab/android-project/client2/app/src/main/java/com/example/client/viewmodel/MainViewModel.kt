package com.example.client.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.client.data.repository.CarRepository
import com.example.client.model.Car
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val carRepository: CarRepository, private val userId: String) : ViewModel() {

    private val _itemsState = MutableStateFlow<ItemsState>(ItemsState.Loading)
    val itemsState: StateFlow<ItemsState> = _itemsState

    private val _selectedCar = MutableStateFlow<Car?>(null)
    val selectedCar: StateFlow<Car?> = _selectedCar

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            while (true) {
                fetchItems()
                delay(10000)
            }
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _itemsState.value = ItemsState.Loading
            try {
                val cars = carRepository.getCarsForUser()
                _itemsState.value = ItemsState.Success(cars)
            } catch (e: Exception) {
                _itemsState.value = ItemsState.Error("Failed to fetch items: ${e.message}")
            }
        }
    }

    fun selectCar(car: Car) {
        _selectedCar.value = car
    }

    fun deselectCar() {
        _selectedCar.value = null
        viewModelScope.launch {
            _uiEvent.send(UiEvent.CarDeselected)
        }
    }


    fun updateCar(car: Car, updatedData: Map<String, Any>, context: Context, sendNotification: (Car, Map<String, Any>) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("MainViewModel","Updating car with ID: ${car.id}")
                val isOnline = isNetworkAvailable(context)
                carRepository.updateCar(car, updatedData, isOnline)
                sendNotification(car, updatedData)
                fetchItems()
                _uiEvent.send(UiEvent.CarUpdated(car.id))
                deselectCar()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to update car", e)
                _uiEvent.send(UiEvent.Error("Failed to update car: ${e.message}"))
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

sealed class ItemsState {
    object Loading : ItemsState()
    data class Success(val items: List<Car>) : ItemsState()
    data class Error(val message: String) : ItemsState()
}

sealed class UiEvent {
    data class CarUpdated(val carId: String) : UiEvent()
    object CarDeselected : UiEvent()
    data class Error(val message: String): UiEvent()
}
