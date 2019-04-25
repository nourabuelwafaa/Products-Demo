package com.demo.productsmarket.data.repository

interface RepoCallback<T> {

    fun onSuccess(t: T)
    fun onError(msg: String?)

}