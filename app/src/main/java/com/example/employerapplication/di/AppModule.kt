package com.example.employerapplication.di

import com.example.employerapplication.BuildConfig
import com.example.employerapplication.data.EmployerApiService
import com.example.employerapplication.data.EmployerRepositoryImpl
import com.example.employerapplication.domain.EmployerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Create By sarita
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    fun createHttpClient(): OkHttpClient {

        /**
         * Creates client for retrofit, here you can configure different settings of retrofit manager
         * like Logging, Cache size, Decoding factories, Convertor factories etc.
         */
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val client = OkHttpClient.Builder()
        client.readTimeout(5 * 60, TimeUnit.SECONDS)
        client.addInterceptor(interceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideApi(baseUrl: String): EmployerApiService {

              return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
            .create(EmployerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: EmployerApiService): EmployerRepository {
        return EmployerRepositoryImpl(api)
    }

}