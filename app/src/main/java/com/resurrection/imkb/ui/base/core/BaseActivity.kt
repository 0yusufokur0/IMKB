package com.resurrection.imkb.ui.base.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

import com.resurrection.imkb.ui.base.data.DataHolder
import com.resurrection.imkb.ui.base.data.DataStoreHelper
import com.resurrection.imkb.ui.base.util.Constants
import javax.inject.Inject


abstract class BaseActivity<viewDataBinding : ViewDataBinding,VM : ViewModel>
    (@LayoutRes private val layoutRes: Int,
     private val viewModelClass: Class<VM>
) : AppCompatActivity() , LifecycleEventObserver {


    @Inject
    lateinit var dataHolder: DataHolder
    @Inject
    lateinit var dataStoreHelper: DataStoreHelper

    lateinit var binding: viewDataBinding


    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        dataStoreHelper.lifecycleOwner = this
        init(savedInstanceState)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_START -> dataHolder.save(Constants.IS_APP_FOREGROUND, true)
            Lifecycle.Event.ON_STOP -> dataHolder.save(Constants.IS_APP_FOREGROUND, false)
            else -> { }
        }
    }
}