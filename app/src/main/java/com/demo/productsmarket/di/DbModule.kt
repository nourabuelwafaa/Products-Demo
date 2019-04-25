package com.demo.productsmarket.di

import android.app.Application
import com.demo.productsmarket.data.local.AppDatabase
import com.demo.productsmarket.data.local.ProductsDao

import dagger.Module
import dagger.Provides


@Module
class DbModule constructor(val application: Application) {


    @Provides
    fun providesDatabase(): AppDatabase {
        return AppDatabase.getInstance(application)
    }


    @Provides
    fun providesProductsDao(appDatabase: AppDatabase): ProductsDao {
        return appDatabase.productsDao()
    }


}