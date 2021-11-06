package com.resurrection.imkb.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.resurrection.imkb.R


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                return true
            }
        }
    }
    toast(context, "No network connection")
    return false
}

fun toast(context: Context, message: String): Toast {
    var toast: Toast =
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Any.isValid(): Boolean {
    var isValid = true
    val fields = this.javaClass.declaredFields

    fields.forEachIndexed { i, field ->

        fields[i].isAccessible = true

        val value = fields[i].get(this)

        Log.w(
            "Msg", "Value of Field "
                    + fields[i].name
                    + " is " + value
        )
        if (value == 0 || value == 0.0 || value == ""|| value == null ){
            isValid = false
        }
    }
    return isValid
}

fun View.setCustomAnimation(anim:Int = R.anim.fall_down) {
    this.startAnimation(AnimationUtils.loadAnimation(this.context,anim))
}

