package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.*
import retrofit2.http.*
import java.time.LocalDate

interface FavouriteService {

    @GET("/api/entities/favourites/{userId}")
    suspend fun getAllFavouriteProductsOfUser(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number): FavouritesResponse

    @POST("/api/entities/favourites/")
    suspend fun makeFavourite(
        @Body favouriteRequest: FavouriteRequest
    )

    @DELETE("/api/entities/favourites/{appuserId}")
    suspend fun quitFavourite(
        @Path("appuserId") appuserId: String,
        @Query("productId") productId: Number
    )
}