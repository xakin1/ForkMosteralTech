package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.User
import com.apm.monsteraltech.data.dto.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("/api/entities/users")
    suspend fun getUserById( @Query("id") userId: String): User

    @GET("/api/entities/users/firebaseToken")
    suspend fun getUserByToken(@Query("firebase_token") tokeId: String): User

    @POST("/api/entities/users")
    suspend fun addUser(
        @Body user: User
    )
    @PUT("/api/entities/users/{id}")
    suspend fun updateUser( @Path("id") userId: String,@Body user: User): User
}