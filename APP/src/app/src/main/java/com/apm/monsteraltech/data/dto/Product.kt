package com.apm.monsteraltech.data.dto

import java.io.Serializable


data class LikedProductImage(
    val id: Long?,
    val name: String?,
    val type: String?,
    val size: Long?,
    val content: String?,
    val product: Product?
) : Serializable {
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (size?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (product?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LikedProductImage

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (size != other.size) return false
        if (content != other.content) return false
        if (product != other.product) return false

        return true
    }
}

data class ProductImage(
    val id: Long?,
    val name: String?,
    val type: String?,
    val size: Long?,
    val content: String?,
    val product: Product?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImage

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (size != other.size) return false
        if (content != other.content) return false
        if (product != other.product) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (size?.hashCode() ?: 0)
        result = 31 * result + (content.hashCode() ?: 0)
        result = 31 * result + (product?.hashCode() ?: 0)
        return result
    }
}

data class ProductImageToDatabase(
    val id: Long?,
    val name: String?,
    val type: String?,
    val size: Long?,
    val content: ByteArray?,
    val product: Product?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImage

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (size != other.size) return false
        if (product != other.product) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (size?.hashCode() ?: 0)
        result = 31 * result + (content.hashCode() ?: 0)
        result = 31 * result + (product?.hashCode() ?: 0)
        return result
    }
}

data class ProductImageResponse(
    val content: List<ProductImage>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val sort: Sort,
    val first: Boolean,
    val empty: Boolean
)

data class ProductResponse(
    val content: List<Product>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val sort: Sort,
    val first: Boolean,
    val empty: Boolean
)
data class LikedProductResponse(
    val content: List<LikedProduct>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val sort: Sort,
    val first: Boolean,
    val empty: Boolean
)


data class LikedProduct(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String?,
    val state: String,
    val images: List<LikedProductImage>?,
    var favourite: Boolean,
    var productOwner: UserProduct
) : Serializable

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String?,
    val state: String,
    val images: List<ProductImage>?,
    val productOwner: UserProduct?,
) : Serializable {
    constructor(id: Long, name: String, price: Double, description: String?, state: String) :
            this(id, name, price, description, state, null, null)
}

data class Car(
    val name: String,
    val description: String?,
    val price: Double,
    val state: String,
    val km: Int,
    val owner: User?,
)

data class House(
    val name: String,
    val description: String?,
    val price: Double,
    val state: String,
    val m2: Int,
    val owner: User?,
)

data class Furniture(
    val name: String,
    val description: String?,
    val price: Double,
    val state: String,
    val owner: User?,
)

data class Appliance(
    val name: String,
    val description: String?,
    val price: Double,
    val state: String,
    val owner: User?,
)

