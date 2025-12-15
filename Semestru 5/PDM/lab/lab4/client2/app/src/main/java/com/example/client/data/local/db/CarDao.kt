package com.example.client.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.client.model.Car

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<Car>)

    @Query("SELECT * FROM cars WHERE id_user = :userId")
    suspend fun getCarsForUser(userId: String): List<Car>

    @Query("SELECT * FROM cars WHERE id = :carId")
    suspend fun getCarById(carId: String): Car?

    @Query("DELETE FROM cars")
    suspend fun deleteAll()

    @Query("DELETE FROM cars WHERE id_user = :userId")
    suspend fun deleteCarsForUser(userId: String)
}