package com.rijks.museum.data.di

import com.rijks.museum.data.BuildConfig
import com.rijks.museum.data.source.remote.MuseumApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class MuseumRemoteDataSourceModule {
    companion object {
        const val BASE_URL = "https://www.rijksmuseum.nl"
        const val KEY_PARAMETER = "key"
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter(KEY_PARAMETER, BuildConfig.API_KEY)
                    .build()

                val request =
                    chain.request().newBuilder().url(url).build()

                chain.proceed(request)
            }
            .build()

    @Provides
    @Named("museumRetrofit")
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    @Provides
    fun provideMuseumApiService(
        @Named("museumRetrofit") retrofit: Retrofit,
    ): MuseumApiService = retrofit.create(MuseumApiService::class.java)
}
