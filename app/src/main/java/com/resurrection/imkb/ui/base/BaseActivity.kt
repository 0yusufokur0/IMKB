package com.resurrection.imkb.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.resurrection.imkb.App

abstract class BaseActivity<viewDataBinding : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: viewDataBinding

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        init(savedInstanceState)
    }

    fun getApp(): App {
        return application as App
    }


}