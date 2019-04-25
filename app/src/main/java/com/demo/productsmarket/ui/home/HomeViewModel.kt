package com.demo.productsmarket.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.demo.productsmarket.data.Product
import com.demo.productsmarket.data.repository.ProductsRepository
import com.demo.productsmarket.data.repository.RepoCallback
import com.demo.productsmarket.ui.home.model.ViewProduct
import com.demo.productsmarket.utils.isConnected
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val appContext: Application
) : ViewModel() {

    private val productsLiveData: MutableLiveData<List<ViewProduct>> = MutableLiveData()
    private val errorMsgLiveData: MutableLiveData<String> = MutableLiveData()
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getProductsLiveD(): LiveData<List<ViewProduct>> = productsLiveData
    fun getErrorMsgLiveD(): LiveData<String> = errorMsgLiveData
    fun getLoadingLiveD(): LiveData<Boolean> = loadingLiveData


    fun getProducts() {
        val isConnected = appContext.applicationContext.isConnected()
        loadingLiveData.value = true
        repository.getProducts(object : RepoCallback<List<Product>> {
            override fun onSuccess(t: List<Product>, isFinished: Boolean) {
                val list = getViewProductList(t)
                productsLiveData.postValue(list)

                if (isFinished)
                    loadingLiveData.postValue(false)
            }

            override fun onError(msg: String?) {
                errorMsgLiveData.postValue(
                    if (!isConnected) "Please check your internet connection"
                    else msg ?: "Connection Error, Try again later.."
                )
                loadingLiveData.postValue(false)

            }

        })

    }

    /** mapper function from List<Product> to List<ViewProduct>
     */
    private fun getViewProductList(list: List<Product>): List<ViewProduct> {
        return list.map {
            ViewProduct(
                it.id,
                it.name,
                it.image,
                "${it.price} EGP"
            )
        }
    }

    override fun onCleared() {
        repository.quit()
        super.onCleared()
    }

}