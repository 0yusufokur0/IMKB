package com.resurrection.imkb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.resurrection.imkb.R
import com.resurrection.imkb.databinding.ActivityTestBinding
import com.resurrection.imkb.ui.base.core.BaseActivity
import com.resurrection.imkb.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity:BaseActivity<ActivityTestBinding,MainViewModel>(R.layout.activity_test,MainViewModel::class.java) {
    override fun init(savedInstanceState: Bundle?) {
        appSession.logger.saveState = true
    }
}


