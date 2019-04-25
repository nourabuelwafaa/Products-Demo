package com.demo.productsmarket.data.repository

import com.demo.productsmarket.data.AppHandler
import com.demo.productsmarket.data.Product
import com.demo.productsmarket.data.ProductResponse
import com.demo.productsmarket.data.local.ProductsDao
import com.demo.productsmarket.data.remote.ApiResponse
import com.demo.productsmarket.data.remote.ProductsApiServices
import com.demo.productsmarket.data.remote.ResponseWrapper

import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val localDataSource: ProductsDao,
    private val remoteDataSource: ProductsApiServices,
    private val appHandler: AppHandler
) : ProductsDataSource {

    override fun insertProducts(products: List<Product>) {
        appHandler.post {
            localDataSource.insertProducts(products)
        }

    }

    override fun getProducts(callback: RepoCallback<List<Product>>) {

        appHandler.post {

            val products = localDataSource.getProducts()
            callback.onSuccess(products)

            remoteDataSource.getProducts().enqueue(object : ResponseWrapper<ProductResponse>() {
                override fun onResponse(apiResponse: ApiResponse<ProductResponse>) {
                    if (apiResponse.response != null)
                        callback.onSuccess(apiResponse.response!!.products)
                    else callback.onError(apiResponse.error)
                }

            })
        }

    }


    fun quit() {
        appHandler.quit()
    }


}