package com.demo.productsmarket.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.demo.productsmarket.data.Product
import com.demo.productsmarket.data.repository.ProductsRepository
import com.demo.productsmarket.data.repository.RepoCallback
import com.demo.productsmarket.ui.home.model.ViewProduct
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    private val productsLiveData: MutableLiveData<List<ViewProduct>> = MutableLiveData()
    private val errorMsgLiveData: MutableLiveData<String> = MutableLiveData()
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getProductsLiveD(): LiveData<List<ViewProduct>> = productsLiveData
    fun getErrorMsgLiveD(): LiveData<String> = errorMsgLiveData
    fun getLoadingLiveD(): LiveData<Boolean> = loadingLiveData


    fun getProducts() {

        loadingLiveData.value = true
        repository.getProducts(object : RepoCallback<List<Product>> {
            override fun onSuccess(t: List<Product>, isFinished: Boolean) {
                val list = getViewProductList(t)
                productsLiveData.postValue(list)

                if (isFinished)
                    loadingLiveData.postValue(false)
            }

            override fun onError(msg: String?) {
                errorMsgLiveData.postValue(msg ?: "Connection Error, Try again later..")
                loadingLiveData.postValue(false)

            }

        })

    }

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