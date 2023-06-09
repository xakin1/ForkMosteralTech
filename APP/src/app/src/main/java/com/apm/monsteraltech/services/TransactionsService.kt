package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.LikedTransaction
import com.apm.monsteraltech.data.dto.Transaction
import com.apm.monsteraltech.data.dto.TransactionsResponse
import retrofit2.Response
import retrofit2.http.*

interface TransactionService {
    @GET("/api/entities/products")
    suspend fun getTransactions(): List<Transaction>

    @GET("/api/entities/transactions/purchases")
    suspend fun getPurchases(@Query("page") page: Number, @Query("size") size: Number,
                               @Query("userId") userId: String): TransactionsResponse

    @GET("/api/entities/transactions/sales")
    suspend fun getSales(@Query("page") page: Number, @Query("size") size: Number,
                               @Query("userId") userId: String): TransactionsResponse

    @GET("/api/entities/transactions/all/{userId}")
    suspend fun getAllTransactions(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number,
                         ): TransactionsResponse

    @GET("/api/entities/transactions/purchases/{userId}")
    suspend fun countPurchases(@Path("userId") userId: String): Number

    @GET("/api/entities/transactions/sales/{userId}")
    suspend fun countSales(@Path("userId") userId: String): Number

    @POST("/api/entities/transactions")
    suspend fun addTransaction(
        @Body transaction: LikedTransaction
    ) : Response<Transaction>

}