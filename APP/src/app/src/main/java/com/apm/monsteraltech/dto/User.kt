package com.apm.monsteraltech.dto

data class UserResponse(
    val content: List<User>,
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

data class Pageable(
    val sort: Sort,
    val offset: Int,
    val pageSize: Int,
    val pageNumber: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val firebaseToken: String,
    val location: String?
)