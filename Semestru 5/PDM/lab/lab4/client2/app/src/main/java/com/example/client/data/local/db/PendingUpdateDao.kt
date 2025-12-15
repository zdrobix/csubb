package com.example.client.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PendingUpdateDao {
    @Insert
    suspend fun insert(update: PendingUpdate)

    @Query("SELECT * FROM pending_updates ORDER BY id ASC")
    suspend fun getAllUpdates(): List<PendingUpdate>

    @Query("DELETE FROM pending_updates WHERE id = :updateId")
    suspend fun delete(updateId: Long)
}