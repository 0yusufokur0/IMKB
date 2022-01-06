package com.resurrection.imkb.ui.base

import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import com.resurrection.imkb.ui.base.general.Logger
import javax.inject.Inject

data class AppSession @Inject constructor(
    val dataHolder: DataHolderManager,
    val sharedPreferences: SharedPreferencesManager,
    val logger: Logger
)