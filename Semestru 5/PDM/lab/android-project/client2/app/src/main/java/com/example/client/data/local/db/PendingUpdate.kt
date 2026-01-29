package com.example.client.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_updates")
data class PendingUpdate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val carId: String,
    val updateType: String,
    val payloadJson: String
)