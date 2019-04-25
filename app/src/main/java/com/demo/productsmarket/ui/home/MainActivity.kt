package com.demo.productsmarket.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.demo.productsmarket.R
import com.demo.productsmarket.di.ApiModule
import com.demo.productsmarket.di.DaggerAppComponent
import com.demo.productsmarket.di.DbModule
import com.demo.productsmarket.ui.home.model.ViewProduct
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

    private val productsList: MutableList<ViewProduct> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectActivity()

        val productsRv = findViewById<RecyclerView>(R.id.productsRv)
        val mainView = findViewById<View>(R.id.mainView)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)


        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        val adapter = ProductsAdapter(productsList) {

        }

        productsRv.adapter = adapter

        observeOnProducts(adapter)

        observeOnErrorMsges(mainView)

        observeOnLoadingIndc(swipeRefresh)


        swipeRefresh.setOnRefreshListener {
            viewModel.getProducts()
        }

        if (savedInstanceState == null) {
            viewModel.getProducts()
        }
    }

    private fun observeOnLoadingIndc(swipeRefresh: SwipeRefreshLayout) {
        viewModel.getLoadingLiveD().observe(this, Observer {
            if (it == null) return@Observer
            swipeRefresh.isRefreshing = it
        })
    }

    private fun observeOnErrorMsges(mainView: View) {
        viewModel.getErrorMsgLiveD().observe(this, Observer {
            if (it == null) return@Observer
            Snackbar.make(mainView, it, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { viewModel.getProducts() }
                .show()

        })
    }

    private fun observeOnProducts(adapter: ProductsAdapter) {
        viewModel.getProductsLiveD().observe(this, Observer {
            if (it == null) return@Observer
            productsList.clear()
            productsList.addAll(it)
            adapter.notifyDataSetChanged()

        })
    }


    private fun injectActivity() {
        DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .dbModule(DbModule(application))
            .build()
            .inject(this)
    }
}
