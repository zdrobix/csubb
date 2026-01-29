package com.example.client.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity(tableName = "cars")
data class Car(
    @SerializedName("_id")
    @PrimaryKey
    val id: String,
    @SerializedName("id_user")
    val id_user: String,
    val name: String,
    val registration_number: String,
    val accident_count: Int,
    val registration_date: Date
)