package com.resurrection.imkb.ui.base

import android.os.Bundle
import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import javax.inject.Inject

data class AppSession @Inject constructor(
    val dataHolder: Bundle,
    val sharedPreferences: SharedPreferencesManager,
)
