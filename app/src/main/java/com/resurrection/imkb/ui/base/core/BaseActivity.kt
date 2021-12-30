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

import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.DataStoreManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import com.resurrection.imkb.ui.base.util.Constants
import javax.inject.Inject

abstract class BaseActivity<viewDataBinding : ViewDataBinding,VM : ViewModel>
    (@LayoutRes private val layoutRes: Int,
     private val viewModelClass: Class<VM>
) : AppCompatActivity() , LifecycleEventObserver {

    @Inject
    lateinit var dataHolderManager: DataHolderManager
    @Inject
    lateinit var dataStoreManager: DataStoreManager
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    lateinit var binding: viewDataBinding

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        dataStoreManager.lifecycleOwner = this
        init(savedInstanceState)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_START -> dataHolderManager.save(Constants.IS_APP_FOREGROUND, true)
            Lifecycle.Event.ON_STOP -> dataHolderManager.save(Constants.IS_APP_FOREGROUND, false)
            else -> { }
        }
    }
}