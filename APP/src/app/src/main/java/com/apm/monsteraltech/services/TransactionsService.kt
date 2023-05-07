package com.apm.monsteraltech.services

import com.apm.monsteraltech.ui.home.Product
import com.apm.monsteraltech.data.dto.Transaction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsService {
    @GET("/api/entities/products")
    suspend fun getProducts(): List<Product>

    @GET("/api/entities/transactions/purchases")
    suspend fun getPurchases(@Query("page") page: Int, @Query("size") size: Int,
                               @Query("userId") userId: String): List<Transaction>

    @GET("/api/entities/transactions/sales")
    suspend fun getSales(@Query("page") page: Int, @Query("size") size: Int,
                               @Query("userId") userId: String): List<Transaction>

    @GET("/api/entities/transactions/purchases/{userId}")
    suspend fun countPurchases(@Path("userId") userId: String): Number

    @GET("/api/entities/transactions/sales/{userId}")
    suspend fun countSales(@Path("userId") userId: String): Number
}