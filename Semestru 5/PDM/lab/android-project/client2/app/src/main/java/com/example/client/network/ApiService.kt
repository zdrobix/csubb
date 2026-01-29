package com.example.client.network

import com.example.client.model.Car
import com.example.client.model.LoginRequest
import com.example.client.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("cars/{id_user}")
    suspend fun getCarsForUser(@Path("id_user") userId: String): Response<List<Car>>

    @PUT("cars/{id_car}")
    suspend fun updateCar(@Path("id_car") id: String, @Body carData: Map<String, @JvmSuppressWildcards Any>): Response<Car>
}