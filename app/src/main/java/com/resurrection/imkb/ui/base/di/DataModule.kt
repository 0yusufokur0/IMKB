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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(preferences: SharedPreferences) = SharedPreferencesManager(preferences)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) =
        DataStoreManager(context.createDataStore(context.packageName))

    @Singleton
    @Provides
    fun provideDataHolder() = Bundle()

    @Singleton
    @Provides
    fun provideDataHolderHelper(bundle: Bundle) = DataHolderManager(bundle)
}