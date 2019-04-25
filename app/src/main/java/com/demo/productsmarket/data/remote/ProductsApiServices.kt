package com.demo.productsmarket.data.remote

import com.demo.productsmarket.data.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductsApiServices {

    @GET("/")
    fun getProducts(): Call<ProductResponse>
}