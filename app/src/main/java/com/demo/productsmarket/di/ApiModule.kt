package com.demo.productsmarket.di

import com.demo.productsmarket.data.remote.ProductsApiServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    @Provides
    internal fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
        httpClient.readTimeout(20, TimeUnit.SECONDS)
        return httpClient.build()
    }


    @Provides
    internal fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://limitless-forest-98976.herokuapp.com/")
            .client(httpClient)
            .build()
    }


    @Provides
    internal fun provideProductsApi(retrofit: Retrofit): ProductsApiServices {
        return retrofit.create(ProductsApiServices::class.java)
    }
}