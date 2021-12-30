package com.resurrection.imkb.ui.base.di

import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.DataStoreManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import javax.inject.Inject

data class AppSession @Inject constructor(
    val dataHolder: DataHolderManager,
    val sharedPreferences: SharedPreferencesManager,
    val dataStore: DataStoreManager
)
