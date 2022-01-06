package com.resurrection.imkb.ui.base.core

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.resurrection.imkb.ui.base.AppSession
import com.resurrection.imkb.ui.base.util.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.Permission
import javax.inject.Inject


abstract class BaseActivity<VDB : ViewDataBinding, VM : ViewModel>
    (
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {
    @Inject
    lateinit var appSession: AppSession
    lateinit var binding: VDB
    protected val viewModel by lazy { ViewModelProvider(this).get(viewModelClass) }
    private val activityName = this@BaseActivity.localClassName

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // show loading indicator
            appSession.logger.activityOnCreate(this@BaseActivity.localClassName)
            binding = DataBindingUtil.setContentView(this@BaseActivity, layoutRes)
            init(savedInstanceState)

    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    //region Lifecycle
    override fun onStart() {
        super.onStart()
        appSession.dataHolder.putBoolean(Constants.IS_APP_FOREGROUND, true)
        appSession.logger.activityOnStart(activityName)

    }

    override fun onResume() {
        super.onResume()
        appSession.logger.activityOnResume(activityName)
    }

    override fun onPause() {
        super.onPause()
        appSession.logger.activityOnPause(activityName)
    }

    override fun onStop() {
        super.onStop()
        appSession.dataHolder.putBoolean(Constants.IS_APP_FOREGROUND, false)
        appSession.logger.activityOnStop(activityName)
    }

    override fun onDestroy() {
        super.onDestroy()
        appSession.logger.activityOnDestroy(activityName)
    }

    override fun onRestart() {
        super.onRestart()
        appSession.logger.activityOnRestart(activityName)
    }
    //endregion
}