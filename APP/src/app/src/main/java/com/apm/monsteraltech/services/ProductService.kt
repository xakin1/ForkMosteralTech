package com.apm.monsteraltech.services

import com.apm.monsteraltech.data.dto.LikedProductResponse
import com.apm.monsteraltech.data.dto.ProductResponse
import com.apm.monsteraltech.enumerados.State
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("/api/entities/products/all/{userId}")
    suspend fun getAllProductsOfUser(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/products")
    suspend fun getAllProducts(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/houses")
    suspend fun getAllHouses(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/furnitures")
    suspend fun getAllFurnitures(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/cars")
    suspend fun getAllCars(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/appliances")
    suspend fun getAllAppliances(@Query("page") page: Number, @Query("size") size: Number): ProductResponse

    @GET("/api/entities/products/all/favourites/{userId}")
    suspend fun getProductsWithFavourites(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number): LikedProductResponse


    @GET("/api/entities/cars/all/favourites/{userId}")
    suspend fun getCarsWithFavourites(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number,
                                      @Query("minPrice") minPrice: Number, @Query("maxPrice") maxPrice: Number, @Query("state") state: State?,
                                      @Query("minKm") minKm: Number, @Query("maxKm") maxKm: Number): LikedProductResponse

    @GET("/api/entities/furnitures/all/favourites/{userId}")
    suspend fun getFurnituresWithFavourites(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number,
                                            @Query("minPrice") minPrice: Number, @Query("maxPrice") maxPrice: Number, @Query("state") state: State?
    ): LikedProductResponse

    @GET("/api/entities/houses/all/favourites/{userId}")
    suspend fun getHousesWithFavourites(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number,
                                        @Query("minPrice") minPrice: Number, @Query("maxPrice") maxPrice: Number, @Query("state") state: State?,
                                        @Query("minM2") minM2: Number, @Query("maxM2") maxM2: Number): LikedProductResponse

    @GET("/api/entities/appliances/all/favourites/{userId}")
    suspend fun getAppliancesWithFavourites(@Path("userId") userId: String, @Query("page") page: Number, @Query("size") size: Number,
                                            @Query("minPrice") minPrice: Number, @Query("maxPrice") maxPrice: Number, @Query("state") state: State?
    ): LikedProductResponse
}