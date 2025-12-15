package com.example.client.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.client.data.repository.CarRepository
import com.example.client.datastore.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val carRepository: CarRepository,
    private val sessionManager: SessionManager
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val pendingUpdates = carRepository.getAllPendingUpdates()
        if (pendingUpdates.isEmpty()) {
            return Result.success()
        }

        var allSucceeded = true
        for (update in pendingUpdates) {
            try {
                val payload = Gson().fromJson(update.payloadJson, Map::class.java) as Map<String, Any>
                val car = carRepository.getCarById(update.carId)
                if (car?.id_user != sessionManager.getSession().first().second)
                    continue
                if(car != null) {
                    carRepository.updateCar(car, payload, true)
                    carRepository.deletePendingUpdate(update.id)
                } else {
                    carRepository.deletePendingUpdate(update.id)
                }
            } catch (e: Exception) {
                allSucceeded = false
            }
        }

        if (allSucceeded) {
            try {
                carRepository.refreshCars()
            } catch (e: Exception) {
                Log.e("SyncWorker", "Failed to refresh cars", e)
            }
            return Result.success()
        } else {
            return Result.retry()
        }
    }
}
