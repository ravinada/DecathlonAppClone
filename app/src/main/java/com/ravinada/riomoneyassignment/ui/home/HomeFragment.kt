package com.ravinada.riomoneyassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.ravinada.riomoneyassignment.R
import com.ravinada.riomoneyassignment.databinding.FragmentHomeBinding
import com.ravinada.riomoneyassignment.ui.base.BaseFragment
import com.ravinada.riomoneyassignment.ui.base.UiState
import com.ravinada.riomoneyassignment.ui.utils.BorderItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment displaying a list of products and providing sorting and searching functionality.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupObserver()
    }

    /**
     * Set up initial data and configurations for the UI components.
     */
    private fun setupData() {
        with(binding) {
            // Add border decoration to the RecyclerView items
            val borderItemDecoration = BorderItemDecoration(
                borderWidth = resources.getDimensionPixelSize(R.dimen.border_width),
                borderColor = ContextCompat.getColor(requireContext(), R.color.boulder)
            )
            adapter = HomeAdapter()
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(borderItemDecoration)
        }
    }

    /**
     * Set up observers for the UI state changes.
     */
    private fun setupObserver() {
        viewModel.observeUiState.collectLatestRepeatOnStarted {
            when (it) {
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(it.data)
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    snackbar(binding.root, it.message.toString(), Toast.LENGTH_LONG)
                }
            }
        }
    }

    /**
     * Perform a search based on the provided query.
     *
     * @param query The search query.
     */
    fun performSearch(query: String) {
        viewModel.searchProductList(query)
    }

    /**
     * Handle the click event when the sort button is clicked.
     *
     * @param view The view representing the sort button.
     */
    fun sortButtonClicked(view: View) {
        showPopupMenu(view)
    }

    /**
     * Show the popup menu for sorting options.
     *
     * @param view The view representing the sort button.
     */
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_sort, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_name -> {
                    viewModel.sortProductListByName()
                    true
                }
                R.id.action_price -> {
                    viewModel.sortProductListByPrice()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    /**
     * Interface for communication with the hosting activity or fragment.
     */
    interface HomeFragmentListener {
        /**
         * Callback when search data is performed.
         *
         * @param query The search query.
         */
        fun onSearchData(query: String)

        /**
         * Callback when the sort button is clicked.
         *
         * @param view The view representing the sort button.
         */
        fun onSortButtonClicked(view: View)
    }
}
