package com.resurrection.imkb.ui.main

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.resurrection.imkb.R
import com.resurrection.imkb.databinding.ActivityMainBinding
import com.resurrection.imkb.ui.base.core.BaseActivity
import com.resurrection.imkb.ui.base.general.hideKeyboard
import com.resurrection.imkb.ui.base.general.onlyTry
import com.resurrection.imkb.ui.base.general.toast
import com.resurrection.imkb.ui.base.general.tryCatch
import com.resurrection.imkb.ui.base.util.changeStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import java.io.*


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main,MainViewModel::class.java),
    NavigationBarView.OnItemSelectedListener {

    lateinit var appBarConfiguration: AppBarConfiguration
    private var textChangedFun: ((String) -> Any?)? = null
    lateinit var navController: NavController

    override fun init(savedInstanceState: Bundle?) {
        setUpNavController()
        requestReadAndWritePermission()

        binding.toolbarTextView.doOnTextChanged { text, start, count, after ->
            text?.let { textChangedFun?.let { it(binding.toolbarTextView.text.toString()) } }
        }
    }

    private fun setUpNavController(){
        changeStatusBarColor(R.color.black)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_favorite))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.navView.setOnItemSelectedListener(this)
    }

    fun setTextChangedFun(mtextChangedFun: ((String) -> Any?)?) {
        textChangedFun = mtextChangedFun
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_test)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val currentFragmentId: Int? = navController.currentDestination?.id

        when (item.itemId) {
            R.id.navigation_home -> {
                if (item.itemId != currentFragmentId) {
                    navController.navigate(
                        R.id.navigation_home, null, NavOptions.Builder()
                            .setEnterAnim(R.anim.left_to_right_first)
                            .setExitAnim(R.anim.left_to_right_second).build()
                    )
                } else navController.navigate(R.id.navigation_home)
            }
            R.id.navigation_favorite -> {
                if (item.itemId != currentFragmentId) {
                    navController.navigate(
                        R.id.navigation_favorite, null, NavOptions.Builder()
                            .setEnterAnim(R.anim.right_to_left_first)
                            .setExitAnim(R.anim.right_to_left_second).build()
                    )
                } else navController.navigate(R.id.navigation_favorite)
            }
        }

        binding.toolbarTextView.text = null
        hideKeyboard(binding.navView)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    // request read and write permission
    fun requestReadAndWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                1
            )
        }
    }




}