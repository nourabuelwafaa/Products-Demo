package com.demo.productsmarket.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(val application: Application) {

    @Provides
    fun providesApplication(): Application {
        return application
    }
}