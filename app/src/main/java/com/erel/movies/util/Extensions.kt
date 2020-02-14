package com.erel.movies.util

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED

fun View.showKeyboard() {
    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}