package com.demo.productsmarket.data.repository

/**
 * Callback for repo callers
 */
interface RepoCallback<T> {

    fun onSuccess(t: T, isFinished: Boolean)
    fun onError(msg: String?)

}