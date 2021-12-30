package com.resurrection.imkb

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication()