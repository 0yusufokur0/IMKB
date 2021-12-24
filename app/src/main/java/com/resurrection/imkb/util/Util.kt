package com.resurrection.imkb.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.resurrection.imkb.App
import com.resurrection.imkb.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> true
        }
    }else return true
}

fun toast(message: String): Toast {
    var toast: Toast =
        Toast.makeText(App.context, message, Toast.LENGTH_SHORT)
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

fun tryCatch(func:()->Unit){
    try {
        func()
    }catch (e:Exception){
        ThrowableError(e.toString())
    }
}

class ThrowableError(msg:String):Throwable(msg){
    init { Log.e("ThrowableError",msg) }
}

fun runLifeCycleScope(block: suspend CoroutineScope.() -> Unit): Job? =
    try {
        val job = App.lifecycleOwner?.lifecycleScope?.launch { block() }
        job
    } catch (e: Exception) {
        ThrowableError(e.toString())
        null
    }

