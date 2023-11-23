package com.ravinada.riomoneyassignment.data.api.response

import com.squareup.moshi.Json

data class ProductResponse(
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "payload")
    val payload: Payload,
    @field:Json(name = "status_code")
    val statusCode: Int
)

data class Payload(
    @field:Json(name = "product")
    val product: List<Product>
)

data class Product(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "design")
    val design: String,
    @field:Json(name = "brand")
    val brand: String,
    @field:Json(name = "imageUrl")
    val imageUrl: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "price")
    val price: Int,
    @field:Json(name = "rating")
    val rating: String,
    @field:Json(name = "strikePrice")
    val strikePrice: Int
)
