package com.resurrection.imkb.util

import android.app.Activity
import android.app.Service
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() = hideKeyboardMain(this, currentFocus!!)
fun Fragment.hideKeyboard() = hideKeyboardMain(requireActivity(), requireActivity().currentFocus!!)
fun Context.hideKeyboard(view: View) = hideKeyboardMain(this, view)
fun Service.hideKeyboard(view: View) = hideKeyboardMain(this, view)

private fun hideKeyboardMain(context: Context,view: View) {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}