package com.resurrection.imkb.ui.base.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.datastore.preferences.createDataStore
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

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context) = SharedPreferencesManager(context)

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) = DataStoreManager(context.createDataStore(context.packageName))

    @Singleton
    @Provides
    fun provideDataHolderManager() = DataHolderManager(Bundle())

    @Singleton
    @Provides
    fun provideAppSession(holder: DataHolderManager,pref: SharedPreferencesManager, store: DataStoreManager) = AppSession(holder,pref,store)
}