package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.Product
import com.apm.monsteraltech.data.dto.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("/api/entities/products/{userId}")
    suspend fun getAllProducts(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number): List<Product>

    @GET("/api/entities/houses")
    suspend fun getAllHouses(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/furnitures")
    suspend fun getAllFurnitures(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/cars")
    suspend fun getAllCars(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/appliances")
    suspend fun getAllAppliances(@Query("page") page: Number, @Query("size") size: Number): ProductResponse
}