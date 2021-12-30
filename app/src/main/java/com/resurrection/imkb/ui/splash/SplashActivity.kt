package com.resurrection.imkb.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.resurrection.imkb.R
import com.resurrection.imkb.databinding.ActivitySplashBinding
import com.resurrection.imkb.ui.base.core.BaseActivity
import com.resurrection.imkb.ui.main.MainActivity
import com.resurrection.imkb.ui.base.util.changeStatusBarColor

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash,SplashViewModel::class.java) {


    override fun init(savedInstanceState: Bundle?) {

        sharedPreferencesManager.insert("data","hello shared preferences")

        this.changeStatusBarColor(R.color.black)

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, i: Int, i1: Int) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout,
                i: Int,
                i1: Int,
                v: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, i: Int) {
                if (motionLayout.currentState == R.id.end) {
                    runBlocking {
                        binding.progressBar.visibility = View.VISIBLE
                        delay(1000)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                i: Int,
                b: Boolean,
                v: Float
            ) {
            }
        })

        lifecycleScope.launch(Dispatchers.IO) {
            delay(500)
            withContext(Dispatchers.Main) {
                binding.motionLayout.transitionToEnd()
            }
        }
    }

}

