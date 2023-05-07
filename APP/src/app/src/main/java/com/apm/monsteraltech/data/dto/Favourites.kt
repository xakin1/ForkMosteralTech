package com.apm.monsteraltech.data.dto

import java.time.LocalDate

data class Favourites(
    val id: Long,
    val date: LocalDate,
    val product: Product,
    val appuser: User
)