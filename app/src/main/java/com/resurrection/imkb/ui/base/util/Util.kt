package com.resurrection.imkb.ui.base.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resurrection.imkb.R
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

fun <T> stringToModel(value: String) = Gson().fromJson(value, object : TypeToken<T>() {}.type) as T
fun <T> modelToString(value: T) = Gson().toJson(value, object : TypeToken<T>() {}.type) as String



fun isNetworkAvailable(context: Context): Boolean {
    try {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> true
        }
    }else false
    }catch (e:Exception){
        return false
    }
}

fun Any.isValid(): Boolean {
    var isValid = true
    val fields = this.javaClass.declaredFields

    fields.forEachIndexed { i, field ->

        fields[i].isAccessible = true

        val value = fields[i].get(this)

        Log.w("Msg", "Value of Field " + fields[i].name + " is " + value)

        if (value == 0 || value == 0.0 || value == "" || value == null) {
            isValid = false
        }
    }
    return isValid
}

fun View.setCustomAnimation(anim: Int = R.anim.fall_down) {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, anim))
}

fun Activity.changeStatusBarColor(color: Int = R.color.black) {
    val window: Window = this.window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}