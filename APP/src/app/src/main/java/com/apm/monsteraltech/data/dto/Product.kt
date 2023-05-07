package com.apm.monsteraltech.data.dto

import com.apm.monsteraltech.enumerados.State
import com.apm.monsteraltech.ui.profile.Transactions


data class ProductImage(
    val id: Long?,
    val name: String?,
    val type: String?,
    val size: Long?,
    val content: ByteArray?,
    val product: Product?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImage

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (size != other.size) return false
        if (content != null) {
            if (other.content == null) return false
            if (!content.contentEquals(other.content)) return false
        } else if (other.content != null) return false
        if (product != other.product) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (size?.hashCode() ?: 0)
        result = 31 * result + (content?.contentHashCode() ?: 0)
        result = 31 * result + (product?.hashCode() ?: 0)
        return result
    }
}

data class Product(
    val id: Long?,
    val name: String,
    val price: Double,
    val description: String,
    val state: State,
    val images: List<ProductImage>,
    val transactions: List<Transactions>,
    val owner: User,
    val favourites: List<Favourites>
)