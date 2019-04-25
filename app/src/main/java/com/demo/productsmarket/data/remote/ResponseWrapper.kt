package com.demo.productsmarket.data.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Class for taking out boilerplate checking for network requests
 */
abstract class ResponseWrapper<T> : Callback<T> {

    private val apiResponse = ApiResponse<T>()

    override fun onResponse(call: Call<T>, response: Response<T>) {
        handleResponse(response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        apiResponse.error = t.message
        onResponse(apiResponse)

    }

    private fun handleResponse(response: Response<T>) {
        if (response.isSuccessful && response.body() != null) {
            apiResponse.response = response.body()!!
        }
        onResponse(apiResponse)

    }


    abstract fun onResponse(apiResponse: ApiResponse<T>)

}
