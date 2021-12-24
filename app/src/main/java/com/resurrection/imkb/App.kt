package com.resurrection.imkb

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication(){
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var activity: Activity
        lateinit var lifecycleOwner: LifecycleOwner
        val context: Context get() = activity
    }
}