package com.example.client.data.repository

import android.util.Log
import com.example.client.data.local.db.CarDao
import com.example.client.data.local.db.PendingUpdate
import com.example.client.data.local.db.PendingUpdateDao
import com.example.client.datastore.SessionManager
import com.example.client.model.Car
import com.example.client.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class CarRepository(private val apiService: ApiService, private val carDao: CarDao, private val pendingUpdateDao: PendingUpdateDao, private val sessionManager: SessionManager) {

    suspend fun getCarsForUser(): List<Car> {
        val userId = sessionManager.getSession().first().second
        if (userId.isNullOrEmpty()) {
            throw Exception("User not logged in")
        }
        // Try to fetch from the local database first
        val localCars = carDao.getCarsForUser(userId)
        if (localCars.isNotEmpty()) {
            return localCars
        }

        // If the local database is empty, fetch from the network
        val response = apiService.getCarsForUser(userId)
        if (response.isSuccessful) {
            val cars = response.body() ?: emptyList()
            // Save the fetched data to the local database
            carDao.insertAll(cars)
            return cars
        } else {
            throw Exception("Failed to fetch cars from network")
        }
    }
    
    suspend fun getCarById(carId: String): Car? {
        return carDao.getCarById(carId)
    }

    suspend fun updateCar(car: Car, updatedData: Map<String, Any>, isOnline: Boolean): Car {
        if (isOnline) {
            val response = apiService.updateCar(car.id, updatedData)
            if (response.isSuccessful) {
                val updatedCar = response.body()!!
                carDao.insertAll(listOf(updatedCar))
                return updatedCar
            } else {
                throw Exception("Failed to update car on network")
            }
        } else {
            Log.d("CarRepository", "Updating car offline. Car ID: ${car.id}")
            queueUpdate(car.id, "UPDATE", updatedData)
            val updatedCar = car.copy()
            carDao.insertAll(listOf(updatedCar))
            return updatedCar
        }
    }

    private suspend fun queueUpdate(carId: String, type: String, payload: Map<String, Any>) {
        val jsonPayload = Gson().toJson(payload)
        val update = PendingUpdate(carId = carId, updateType = type, payloadJson = jsonPayload)
        pendingUpdateDao.insert(update)
    }

    suspend fun getAllPendingUpdates(): List<PendingUpdate> {
        return pendingUpdateDao.getAllUpdates()
    }

    suspend fun deletePendingUpdate(updateId: Long) {
        pendingUpdateDao.delete(updateId)
    }

    suspend fun refreshCars() {
        val userId = sessionManager.getSession().first().second
        if (userId.isNullOrEmpty()) {
            return
        }
        Log.d("CarRepository", "Refresh for user: $userId")
        val response = apiService.getCarsForUser(userId)
        if (response.isSuccessful) {
            val cars = response.body() ?: emptyList()
            carDao.deleteCarsForUser(userId)
            carDao.insertAll(cars)
        } else {
            throw Exception("Failed to refresh cars from network")
        }
    }
}