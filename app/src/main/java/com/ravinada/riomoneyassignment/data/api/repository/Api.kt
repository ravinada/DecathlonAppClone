package com.ravinada.riomoneyassignment.data.api.repository

import com.ravinada.riomoneyassignment.data.api.response.ProductResponse
import retrofit2.http.GET

/**
 * Retrofit API interface for making network requests related to products.
 */
interface Api {

    /**
     * Get a list of products from the API.
     *
     * @return A [ProductResponse] containing the list of products.
     */
    @GET("decathlon/products")
    suspend fun getProductList(): ProductResponse
}
