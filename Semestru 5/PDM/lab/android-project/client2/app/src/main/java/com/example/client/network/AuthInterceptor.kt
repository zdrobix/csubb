package com.example.client.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    // This should be replaced with a secure way of storing and retrieving the token
    private var token: String? = null
    private var userId: Int? = null

    fun setToken(token: String?) {
        this.token = token
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}