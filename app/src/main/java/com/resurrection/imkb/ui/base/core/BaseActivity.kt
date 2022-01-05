package com.resurrection.imkb.ui.base.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.resurrection.imkb.ui.base.AppSession
import com.resurrection.imkb.ui.base.util.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


abstract class BaseActivity<VDB : ViewDataBinding, VM : ViewModel>
    (
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    @Inject
    lateinit var appSession: AppSession

    lateinit var binding: VDB

    protected val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // show loading indicator
        lifecycleScope.launch {
            runBlocking {
                appSession.lifecycleOwner = this@BaseActivity
                binding = DataBindingUtil.setContentView(this@BaseActivity, layoutRes)
                appSession.logger.activityOnCreate(viewModelClass.simpleName)
            }
        }
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        appSession.dataHolder.putBoolean(Constants.IS_APP_FOREGROUND, true)
        appSession.logger.activityOnStart(viewModelClass.simpleName)

    }

    override fun onResume() {
        super.onResume()
        appSession.logger.activityOnResume(viewModelClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        appSession.logger.activityOnPause(viewModelClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        appSession.dataHolder.putBoolean(Constants.IS_APP_FOREGROUND, false)
        appSession.logger.activityOnStop(viewModelClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        appSession.logger.activityOnDestroy(viewModelClass.simpleName)
    }

    override fun onRestart() {
        super.onRestart()
        appSession.logger.activityOnRestart(viewModelClass.simpleName)
    }


    fun requestWriteReadExternalStoragePermission() {
        // appSession.permissionManager.requestWriteReadExternalStoragePermission(this)
    }
}