package com.ravinada.riomoneyassignment.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ravinada.riomoneyassignment.R
import com.ravinada.riomoneyassignment.databinding.ActivityMainBinding
import com.ravinada.riomoneyassignment.ui.base.BaseActivity
import com.ravinada.riomoneyassignment.ui.home.HomeFragment
import com.ravinada.riomoneyassignment.ui.utils.addTapToDismissBehaviour
import com.ravinada.riomoneyassignment.ui.utils.onActionSearch

/**
 * Main activity hosting various fragments.
 */
class MainActivity : BaseActivity(), HomeFragment.HomeFragmentListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RioMoneyAssignment)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the HomeFragment as the initial fragment
        loadFragment(HomeFragment(), HomeFragment::class.java.simpleName)

        // Set up listeners and UI configurations
        setupListener()

        // Set up spannable text for location information
        setupSpannable()
    }

    /**
     * Set up various listeners and UI configurations.
     */
    private fun setupListener() {
        with(binding) {
            // Add tap-to-dismiss behavior to the root view
            root.addTapToDismissBehaviour()

            // Handle item selection in the bottom navigation view
            bottomNavView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_home -> {
                        loadFragment(HomeFragment(), HomeFragment::class.java.simpleName)
                        true
                    }
                    else -> false
                }
            }

            // Handle text changes in the search EditText
            includeAppBar.editTextSearch.doOnTextChanged { text, _, _, _ ->
                onSearchData(text.toString())
            }

            // Handle search action
            includeAppBar.editTextSearch.onActionSearch {
                hideKeyboard()
                true
            }

            // Handle click on the sort button
            includeAppBar.imageViewSort.setOnClickListener {
                onSortButtonClicked(it)
            }

            // Placeholder for handling click on the location TextView
            includeAppBar.textViewLocation.setOnClickListener {
                toast("Not Implemented")
            }
        }
    }

    /**
     * Set up spannable text for the location information.
     */
    private fun setupSpannable() {
        val locationCode = getString(R.string.dummy_location_pinCode)
        val changeText = getString(R.string.change)

        val fullText = getString(R.string.dummy_location_text, locationCode, changeText)

        val spannableString = SpannableString(fullText)

        val startIndex = fullText.indexOf(locationCode)
        val endIndex = startIndex + locationCode.length

        // Apply yellow color to the location code
        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.yellow)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Apply underline style to the "CHANGE" text
        val underlineSpan = UnderlineSpan()
        val changeIndex = fullText.indexOf(changeText)
        spannableString.setSpan(
            underlineSpan,
            changeIndex,
            changeIndex + changeText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the spannable string to the TextView
        binding.includeAppBar.textViewLocation.text = spannableString
    }

    /**
     * Load a fragment into the fragment container.
     *
     * @param fragment The fragment to load.
     * @param tag The tag for the fragment.
     */
    private fun loadFragment(fragment: Fragment, tag: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Callback when search data is performed.
     *
     * @param query The search query.
     */
    override fun onSearchData(query: String) {
        val homeFragment =
            supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName) as? HomeFragment
        homeFragment?.performSearch(query)
    }

    /**
     * Callback when the sort button is clicked.
     *
     * @param view The view representing the sort button.
     */
    override fun onSortButtonClicked(view: View) {
        val homeFragment =
            supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName) as? HomeFragment
        homeFragment?.sortButtonClicked(binding.includeAppBar.imageViewSort)
    }
}
