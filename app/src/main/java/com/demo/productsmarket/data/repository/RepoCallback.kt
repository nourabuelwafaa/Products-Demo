package com.demo.productsmarket.data.repository

interface RepoCallback<T> {

    fun onSuccess(t: T, isFinished: Boolean)
    fun onError(msg: String?)

}