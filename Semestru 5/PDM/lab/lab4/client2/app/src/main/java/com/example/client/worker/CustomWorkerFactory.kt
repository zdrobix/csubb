package com.example.client.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.client.data.repository.CarRepository
import com.example.client.datastore.SessionManager
import com.example.client.worker.SyncWorker

class CustomWorkerFactory(
    private val carRepository: CarRepository,
    private val sessionManager: SessionManager
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(appContext, workerParameters, carRepository, sessionManager)

            else ->
                null
        }
    }
}