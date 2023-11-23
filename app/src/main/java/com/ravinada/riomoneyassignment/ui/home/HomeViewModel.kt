package com.ravinada.riomoneyassignment.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravinada.riomoneyassignment.data.api.repository.ApiRepository
import com.ravinada.riomoneyassignment.data.api.response.Product
import com.ravinada.riomoneyassignment.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel for the HomeFragment.
 *
 * @param apiRepository The repository responsible for providing data from the API.
 */
class HomeViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    val uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)

    private var productList: List<Product> = emptyList()

    val observeUiState: StateFlow<UiState<List<Product>>> = uiState

    // Initialization block that fetches the initial product list
    init {
        fetchProductList()
    }

    /**
     * Sort the product list by name.
     */
    fun sortProductListByName() {
        viewModelScope.launch {
            try {
                val currentUiState = observeUiState.value
                if (currentUiState is UiState.Success) {
                    val sortedProductList = currentUiState.data.sortedBy { it.name }
                    uiState.value = UiState.Success(sortedProductList)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.toString(), null)
            }
        }
    }

    /**
     * Sort the product list by price.
     */
    fun sortProductListByPrice() {
        viewModelScope.launch {
            try {
                val currentUiState = observeUiState.value
                if (currentUiState is UiState.Success) {
                    val sortedProductList = currentUiState.data.sortedBy { it.price }
                    uiState.value = UiState.Success(sortedProductList)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.toString(), null)
            }
        }
    }

    /**
     * Search the product list based on the provided query.
     *
     * @param query The search query.
     */
    fun searchProductList(query: String) {
        viewModelScope.launch {
            try {
                val currentUiState = observeUiState.value
                if (currentUiState is UiState.Success) {
                    val filteredList = if (query.isBlank()) {
                        productList
                    } else {
                        currentUiState.data.filter {
                            it.name.contains(query, ignoreCase = true) ||
                                it.brand.contains(query, ignoreCase = true)
                        }
                    }
                    uiState.value = UiState.Success(filteredList)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.toString(), null)
            }
        }
    }

    /**
     * Fetch the initial product list from the repository.
     */
    fun fetchProductList() {
        viewModelScope.launch {
            // Use flow to collect product list from the repository
            apiRepository.getProductList().catch { e ->
                uiState.value = UiState.Error(e.toString(), null)
            }.collect {
                productList = it
                uiState.value = UiState.Success(it)
            }
        }
    }
}
