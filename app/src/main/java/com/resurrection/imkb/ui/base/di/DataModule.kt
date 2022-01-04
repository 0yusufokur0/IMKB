package com.resurrection.imkb.ui.base.di

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.resurrection.imkb.ui.base.AppSession
import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.DataStoreManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "")

    @Provides
    @Singleton
    fun provideDataHolder(@ApplicationContext context: Context) = DataHolderManager(Bundle())

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context) = SharedPreferencesManager(context)

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) = DataStoreManager(context.dataStore)

    @Singleton
    @Provides
    fun provideAppSession(dataHolder: DataHolderManager,sharedPref: SharedPreferencesManager,dataStore: DataStoreManager) =
        AppSession(dataHolder,sharedPref,dataStore)

}