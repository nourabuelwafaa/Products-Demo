package com.demo.productsmarket.di

import com.demo.productsmarket.data.AppHandler
import com.demo.productsmarket.data.local.ProductsDao
import com.demo.productsmarket.data.remote.ProductsApiServices
import com.demo.productsmarket.data.repository.ProductsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesAppHandler(): AppHandler {
        return AppHandler()
    }

    @Provides
    fun productRepository(
        productDao: ProductsDao,
        remoteSource: ProductsApiServices,
        appHandler: AppHandler
    ): ProductsRepository {
        return ProductsRepository(productDao, remoteSource, appHandler)
    }
}