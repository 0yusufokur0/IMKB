package com.resurrection.imkb.ui.base.di

import android.content.Context
import android.os.Bundle
import com.resurrection.imkb.ui.base.AppSession
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
    fun provideAppSession(sharedPref: SharedPreferencesManager) = AppSession(Bundle(),sharedPref)

}