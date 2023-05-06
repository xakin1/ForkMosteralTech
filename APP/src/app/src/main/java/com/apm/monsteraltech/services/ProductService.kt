package com.apm.monsteraltech.services

import com.apm.monsteraltech.ui.home.Product
import retrofit2.http.GET

interface ProductService {
    @GET("/api/entities/products")
    suspend fun getProducts(): List<Product>
}