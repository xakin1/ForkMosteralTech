package com.apm.monsteraltech.data.dto

import com.apm.monsteraltech.enumerados.State
import java.time.LocalDateTime

data class TransactionsResponse(
    val content: List<Transaction>,
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
class Transaction(
    val id: Long,
    val date: LocalDateTime,
    val price: Double,
    val state: State,
    val product: Product,
    val seller: User,
    val buyer: User,
    val description: String?,
    val name: String
)