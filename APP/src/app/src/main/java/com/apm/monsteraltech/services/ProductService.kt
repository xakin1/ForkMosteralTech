package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("/api/entities/products/{userId}")
    suspend fun getallProducts(@Path("userId") userId: String, @Query("page") page: Int, @Query("size") size: Int): List<Product>
}