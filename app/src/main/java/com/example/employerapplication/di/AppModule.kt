package com.example.employerapplication.di

import com.example.employerapplication.BuildConfig
import com.example.employerapplication.data.EmployerApiService
import com.example.employerapplication.data.EmployerRepositoryImpl
import com.example.employerapplication.domain.EmployerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Create By sarita
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideApi(baseUrl: String): EmployerApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: EmployerApiService): EmployerRepository {
        return EmployerRepositoryImpl(api)
    }

}