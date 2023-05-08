package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.Transaction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionService {
    @GET("/api/entities/products")
    suspend fun getTransactions(): List<Transaction>

    @GET("/api/entities/transactions/purchases")
    suspend fun getPurchases(@Query("page") page: Int, @Query("size") size: Int,
                               @Query("userId") userId: String): List<Transaction>

    @GET("/api/entities/transactions/sales")
    suspend fun getSales(@Query("page") page: Int, @Query("size") size: Int,
                               @Query("userId") userId: String): List<Transaction>

    @GET("/api/entities/transactions/all/{userId}")
    suspend fun getAllTransactions(@Query("page") page: Int, @Query("size") size: Int,
                         @Path("userId") userId: String): List<Transaction>

    @GET("/api/entities/transactions/purchases/{userId}")
    suspend fun countPurchases(@Path("userId") userId: String): Number

    @GET("/api/entities/transactions/sales/{userId}")
    suspend fun countSales(@Path("userId") userId: String): Number
}