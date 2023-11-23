package com.ravinada.riomoneyassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ravinada.riomoneyassignment.data.api.repository.ApiRepository
import com.ravinada.riomoneyassignment.data.api.response.Product
import com.ravinada.riomoneyassignment.ui.base.UiState
import com.ravinada.riomoneyassignment.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var viewModel: HomeViewModel

    private val testData = listOf(
        Product(
            id = 1,
            design = "Some Design 1",
            brand = "Some Brand 1",
            imageUrl = "https://example.com/image1.jpg",
            name = "Product Name 1",
            price = 100,
            rating = "4.5",
            strikePrice = 399
        ),
        Product(
            id = 2,
            design = "Some Design 2",
            brand = "Some Brand 2",
            imageUrl = "https://example.com/image2.jpg",
            name = "Product Name 2",
            price = 130,
            rating = "4.3",
            strikePrice = 299
        ),
        Product(
            id = 3,
            design = "Some Design 3",
            brand = "Some Brand 3",
            imageUrl = "https://example.com/image3.jpg",
            name = "Product Name 3",
            price = 170,
            rating = "4.7",
            strikePrice = 299
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = HomeViewModel(apiRepository)
    }

    @Test
    fun testSortProductListByName() = runTest {
        viewModel.uiState.value = UiState.Success(testData)

        viewModel.sortProductListByName()

        val sortedData = (viewModel.observeUiState.value as UiState.Success<List<Product>>).data
        assertEquals("Product Name 1", sortedData[0].name)
        assertEquals("Product Name 2", sortedData[1].name)
        assertEquals("Product Name 3", sortedData[2].name)
    }

    @Test
    fun testSortProductListByPrice() = runTest {
        viewModel.uiState.value = UiState.Success(testData)

        viewModel.sortProductListByPrice()

        val sortedData = (viewModel.observeUiState.value as UiState.Success<List<Product>>).data
        assertEquals(100, sortedData[0].price)
        assertEquals(130, sortedData[1].price)
        assertEquals(170, sortedData[2].price)
    }

    @Test
    fun testSearchProductList() = runTest {
        viewModel.uiState.value = UiState.Success(testData)

        viewModel.searchProductList("Some Brand 2")

        val filteredData = (viewModel.observeUiState.value as UiState.Success<List<Product>>).data
        assertEquals(1, filteredData.size)
        assertEquals("Some Brand 2", filteredData[0].brand)
    }

    @Test
    fun testFetchProductList() = runTest {
        val testDataFlow = flowOf(testData)
        `when`(apiRepository.getProductList()).thenReturn(testDataFlow)

        viewModel.fetchProductList()

        val fetchedData = (viewModel.observeUiState.value as UiState.Success<List<Product>>).data
        assertEquals(testData, fetchedData)
    }
}
