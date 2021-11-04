package com.resurrection.imkb.di

import com.resurrection.imkb.data.repository.ImkbRepository
import com.resurrection.imkb.data.repository.ImkbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(repository: ImkbRepositoryImpl): ImkbRepository
}