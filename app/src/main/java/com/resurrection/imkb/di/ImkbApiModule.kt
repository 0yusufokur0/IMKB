package com.resurrection.imkb.di

import com.resurrection.imkb.data.remote.HandshakeApiService
import com.resurrection.imkb.data.remote.ImkbApiService
import com.resurrection.imkb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImkbApiModule {

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun createApi(retrofit: Retrofit): ImkbApiService =
        retrofit.create(ImkbApiService::class.java)

    @Singleton
    @Provides
    fun provideHandshakeService(): HandshakeApiService {
        return retrofitClient().create(HandshakeApiService::class.java)
    }
}
