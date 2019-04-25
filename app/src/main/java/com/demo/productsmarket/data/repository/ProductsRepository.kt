package com.demo.productsmarket.data.repository

import com.demo.productsmarket.data.AppHandler
import com.demo.productsmarket.data.Product
import com.demo.productsmarket.data.ProductResponse
import com.demo.productsmarket.data.local.ProductsDao
import com.demo.productsmarket.data.remote.ApiResponse
import com.demo.productsmarket.data.remote.ProductsApiServices
import com.demo.productsmarket.data.remote.ResponseWrapper

import javax.inject.Inject


open class ProductsRepository @Inject constructor(
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
            callback.onSuccess(products, isFinished = false)

            remoteDataSource.getProducts().enqueue(object : ResponseWrapper<ProductResponse>() {
                override fun onResponse(apiResponse: ApiResponse<ProductResponse>) {
                    if (apiResponse.response != null) {
                        callback.onSuccess(apiResponse.response!!.products, isFinished = true)
                        insertProducts(apiResponse.response!!.products)
                    } else callback.onError(apiResponse.error)
                }

            })
        }

    }

    override fun getProduct(productId: Int, repoCallback: RepoCallback<Product>) {

        appHandler.post {

            val product = localDataSource.getProduct(productId)
            repoCallback.onSuccess(product, isFinished = true)

        }
    }


    fun quit() {
        appHandler.quit()
    }


}