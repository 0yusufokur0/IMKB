package com.resurrection.imkb.ui.base

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.DataStoreManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import com.resurrection.imkb.ui.base.general.Logger
import javax.inject.Inject

data class AppSession @Inject constructor(
    val dataHolder: DataHolderManager,
    val sharedPreferences: SharedPreferencesManager,
    val dataStore: DataStoreManager,
    val logger: Logger
){
    var lifecycleOwner: LifecycleOwner? = null
    init {
        lifecycleOwner?.let {
            dataStore.lifecycleOwner = lifecycleOwner!!
            logger.lifecycleOwner = lifecycleOwner!!
        }
    }
}
