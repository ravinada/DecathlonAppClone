package com.ravinada.riomoneyassignment.ui.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.getSystemService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadUrl(url: String, placeholderResId: Int, errorResId: Int) {
    val requestOptions = RequestOptions().centerCrop()
    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .placeholder(placeholderResId)
        .error(errorResId)
        .into(this)
}

/**
 * Consumes event on [EditorInfo.IME_ACTION_SEARCH]
 *
 * @param block
 */
fun EditText.onActionSearch(block: () -> Boolean) {
    setOnEditorActionListener { _, actionId, _ ->

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            block()
            clearFocus()
        }

        false
    }
}

/**
 * Adds tap to dismiss behaviour to [View]
 * @receiver View
 */
@SuppressLint("ClickableViewAccessibility")
fun View.addTapToDismissBehaviour() {
    isFocusable = true
    isFocusableInTouchMode = true
    isClickable = true
    setOnTouchListener { view, event ->
        var returnValue = false
        event?.let { ev ->
            if (ev.action == MotionEvent.ACTION_UP) {
                view.requestFocus()
                hideKeyboard()
                returnValue = true
            }
        }
        returnValue
    }
}

/**
 * Hides the on screen keyboard.
 */
fun View?.hideKeyboard() {
    this?.let {
        context.getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
