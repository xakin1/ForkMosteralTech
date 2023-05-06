package com.apm.monsteraltech.services

import com.apm.monsteraltech.dto.User
import com.apm.monsteraltech.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/api/entities/users")
    suspend fun getUser()

    @GET("/api/entities/users")
    suspend fun getUserById(@Query("id") userId: Number): UserResponse

    @POST("/api/entities/users")
    suspend fun addUser()
}