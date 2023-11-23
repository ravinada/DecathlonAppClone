package com.ravinada.riomoneyassignment.data.api.repository

import com.ravinada.riomoneyassignment.data.api.response.Product
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    fun getProductList(): Flow<List<Product>>
}
