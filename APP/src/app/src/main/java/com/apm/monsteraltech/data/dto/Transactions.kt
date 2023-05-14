package com.apm.monsteraltech.data.dto

import com.apm.monsteraltech.enumerados.State
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    val date: List<Int>,
    val price: Double,
    val state: State?,
    val location: String?,
    val product: Product,
    val seller: User,
    val buyer: User,
    val description: String?,
    val name: String
){
    fun getDate(): String{
        val date = LocalDateTime.of(date[0], date[1], date[2], date[3], date[4])
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    }
}