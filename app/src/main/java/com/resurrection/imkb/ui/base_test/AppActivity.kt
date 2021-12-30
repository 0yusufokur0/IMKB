package com.resurrection.imkb.ui.base_test

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.resurrection.imkb.util.Constants
import com.resurrection.imkb.util.data.DataHolder
import javax.inject.Inject


abstract class AppActivity<viewDataBinding : ViewDataBinding>(@LayoutRes val resLayoutId: Int) : AppCompatActivity(),
    LifecycleEventObserver {

    lateinit var binding: viewDataBinding

    @Inject
    lateinit var dataHolder: DataHolder

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, resLayoutId)

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