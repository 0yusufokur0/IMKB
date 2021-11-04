package com.resurrection.imkb.di

import android.content.Context
import androidx.room.Room
import com.resurrection.imkb.data.db.ImkbDatabase
import com.resurrection.imkb.data.db.dao.ImkbDao

import com.resurrection.imkb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImkbDaoModule {

    @Provides
    @Singleton
    fun movieDatabase(@ApplicationContext context: Context): ImkbDatabase =
        Room.databaseBuilder(context, ImkbDatabase::class.java, Constants.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun movieDao(imkbDatabase: ImkbDatabase): ImkbDao =
        imkbDatabase.imkbDao()
}

