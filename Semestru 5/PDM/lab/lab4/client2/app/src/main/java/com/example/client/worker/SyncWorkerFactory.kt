package com.example.client.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.client.data.repository.CarRepository
import com.example.client.datastore.SessionManager

class SyncWorkerFactory(
    private val carRepository: CarRepository,
    private val sessionManager: SessionManager
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return if (workerClassName == SyncWorker::class.java.name) {
            SyncWorker(appContext, workerParameters, carRepository, sessionManager)
        } else {
            null
        }
    }
}