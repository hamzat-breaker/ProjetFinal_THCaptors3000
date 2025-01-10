package com.example.thcaptorsapp.api

import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

data class Measurement(
    val _id: String,
    val timestamp: String,
    val temperature: Float,
    val humidity: Float
)

interface ApiService {
    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/data")
    fun getMeasurements(@Header("Authorization") token: String): Call<List<Measurement>>
}
