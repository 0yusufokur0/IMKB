package com.resurrection.imkb.ui.base

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.resurrection.imkb.App

abstract class BaseActivity<viewDataBinding : ViewDataBinding>(@LayoutRes val resLayoutId: Int) : AppCompatActivity() {

    lateinit var binding: viewDataBinding

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.activity = this
        App.lifecycleOwner = this
        binding = DataBindingUtil.setContentView(this, resLayoutId)
        init(savedInstanceState)
    }

}