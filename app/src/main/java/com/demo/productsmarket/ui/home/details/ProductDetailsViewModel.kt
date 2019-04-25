package com.demo.productsmarket.ui.home.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.demo.productsmarket.data.Product
import com.demo.productsmarket.data.repository.ProductsRepository
import com.demo.productsmarket.data.repository.RepoCallback
import com.demo.productsmarket.ui.home.model.ViewProductDetails
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    private val productLiveData: MutableLiveData<ViewProductDetails> = MutableLiveData()

    fun getProductLiveD(): LiveData<ViewProductDetails> = productLiveData

    fun getProduct(productId: Int) {

        repository.getProduct(productId, object : RepoCallback<Product> {
            override fun onSuccess(t: Product, isFinished: Boolean) {
                val viewProductDetails = getViewProductDetails(t)
                productLiveData.postValue(viewProductDetails)
            }

            override fun onError(msg: String?) {
                // Not used here;
            }

        })

    }

    private fun getViewProductDetails(product: Product): ViewProductDetails {
        return ViewProductDetails(
            product.name,
            product.image,
            "${product.price} EGP",
            product.desc
        )
    }


}