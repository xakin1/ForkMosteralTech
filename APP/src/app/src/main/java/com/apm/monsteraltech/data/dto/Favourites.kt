package com.apm.monsteraltech.data.dto

import java.time.LocalDate


data class FavouritesResponse(
    val content: List<Favourites>,
    val pageable: Pageable,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val sort: Sort,
    val first: Boolean,
    val empty: Boolean
)

data class FavouriteRequest(
    val user: User,
    val product: LikedProduct,
    val date: String
)

data class Favourites(
    val id: Long,
    val date: List<Int>,
    val product: LikedProduct,
    val user: User
)