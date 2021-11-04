package com.resurrection.imkb.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private var jobIO: Job = Job()
    private var jobMain: Job = Job()

    private val coroutineContextIO: CoroutineContext get() = jobIO + Dispatchers.IO
    private val coroutineContextMain: CoroutineContext get() = Dispatchers.Main

    fun launchOnIO(function: suspend () -> Unit) {
        jobIO = CoroutineScope(coroutineContextIO).launch { function() }
    }

    fun launchOnMain(function: suspend () -> Unit) {
        jobMain = CoroutineScope(coroutineContextMain).launch { function() }
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.IO


    override fun onCleared() {
        super.onCleared()
        jobIO.cancel()
        jobMain.cancel()
    }
}