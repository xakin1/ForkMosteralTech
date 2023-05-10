package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.FavouritesResponse
import com.apm.monsteraltech.data.dto.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FavouriteService {

    @GET("/api/entities/favourites/{userId}")
    suspend fun getAllFavouriteProductsOfUser(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number): FavouritesResponse

}