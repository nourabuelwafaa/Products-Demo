package com.demo.productsmarket.data.repository

import com.demo.productsmarket.data.Product


interface ProductsDataSource {

    fun insertProducts(products: List<Product>)

    fun getProducts(callback: RepoCallback<List<Product>>)
}