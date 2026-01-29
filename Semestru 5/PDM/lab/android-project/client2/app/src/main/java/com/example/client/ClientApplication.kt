package com.example.client

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.client.data.local.db.AppDatabase
import com.example.client.data.repository.CarRepository
import com.example.client.datastore.SessionManager
import com.example.client.network.RetrofitClient
import com.example.client.worker.SyncWorkerFactory

class ClientApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val database by lazy { AppDatabase.getDatabase(this) }
        val sessionManager by lazy { SessionManager(this) }
        val carRepository by lazy { CarRepository(RetrofitClient.instance, database.carDao(), database.pendingUpdateDao(), sessionManager) }
        
        val factory = SyncWorkerFactory(carRepository, sessionManager)
        val config = Configuration.Builder()
            .setWorkerFactory(factory)
            .build()
        WorkManager.initialize(this, config)
    }
}