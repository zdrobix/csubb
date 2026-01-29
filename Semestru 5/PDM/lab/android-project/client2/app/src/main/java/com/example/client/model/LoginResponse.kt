package com.example.client.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    @SerializedName("user_id") val userId: String
)