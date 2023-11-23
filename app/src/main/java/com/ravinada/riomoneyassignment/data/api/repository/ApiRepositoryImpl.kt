package com.ravinada.riomoneyassignment.data.api.repository

import com.ravinada.riomoneyassignment.data.api.response.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the [ApiRepository] interface responsible for fetching products from the API.
 *
 * @param api The Retrofit API interface for making network requests.
 */
class ApiRepositoryImpl(private val api: Api) : ApiRepository {

    /**
     * Get a flow of product data from the API.
     *
     * @return A flow emitting a list of products.
     */
    override fun getProductList(): Flow<List<Product>> {
        return flow {
            // Emit the product response from the API
            emit(api.getProductList())
        }.map {
            // Map the product response to the list of products
            it.payload.product
        }
    }
}
