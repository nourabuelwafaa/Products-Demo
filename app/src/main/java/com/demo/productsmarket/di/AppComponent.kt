package com.demo.productsmarket.di

import com.demo.productsmarket.ui.home.MainActivity
import com.demo.productsmarket.ui.home.details.ProductDetailsActivity
import dagger.Component

@Component(
    modules = arrayOf(
        AppModule::class,
        DbModule::class,
        ApiModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    )
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: ProductDetailsActivity)

}