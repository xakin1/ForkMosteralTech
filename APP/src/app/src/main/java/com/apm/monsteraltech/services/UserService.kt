package com.apm.monsteraltech.services

import com.apm.monsteraltech.dto.User
import com.apm.monsteraltech.dto.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("/api/entities/users")
    suspend fun getUser()

    @GET("/api/entities/users/{id}")
    suspend fun getUserById( @Path("id") userId: String): User

    @GET("/api/entities/users")
    suspend fun getUserByToken(@Query("firebaseToken") tokeId: String): UserResponse

    @POST("/api/entities/users")
    suspend fun addUser(
        @Body user: User
    )
}