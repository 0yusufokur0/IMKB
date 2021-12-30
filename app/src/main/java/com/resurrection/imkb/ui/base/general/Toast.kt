package com.resurrection.imkb.ui.base.general

import android.app.Activity
import android.app.Service
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(message: String)= toast(this, message, Toast.LENGTH_SHORT)
fun Fragment.toast(message: String)= toast(requireContext(), message, Toast.LENGTH_SHORT)
fun Context.toast(message: String)= toast(this, message, Toast.LENGTH_SHORT)
fun Service.toast(message: String)= toast(this, message, Toast.LENGTH_SHORT)
fun Activity.toastLong(message: String)= toast(this, message, Toast.LENGTH_LONG)
fun Fragment.toastLong(message: String)= toast(requireContext(), message, Toast.LENGTH_LONG)
fun Context.toastLong(message: String)= toast(this, message, Toast.LENGTH_LONG)
fun Service.toastLong(message: String)= toast(this, message, Toast.LENGTH_LONG)

private fun toast(context: Context,message: String, duration: Int): Toast {
    val toast = Toast.makeText(context, message, duration)
    toast.show()
    return toast
}