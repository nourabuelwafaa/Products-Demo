package com.demo.productsmarket.ui.home.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.demo.productsmarket.R
import com.demo.productsmarket.di.ApiModule
import com.demo.productsmarket.di.DaggerAppComponent
import com.demo.productsmarket.di.DbModule
import com.demo.productsmarket.utils.adjustHeight
import com.demo.productsmarket.utils.loadUrl
import javax.inject.Inject

class ProductDetailsActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        injectActivity()

        val titleTv: TextView = findViewById(R.id.titleTv)
        val priceTv: TextView = findViewById(R.id.priceTv)
        val descTv: TextView = findViewById(R.id.descTv)
        val productIv: ImageView = findViewById(R.id.productIv)

        val productId = intent.getIntExtra(PRODUCT_ID_KEY, 0)

        viewModel = ViewModelProviders.of(this, factory).get(ProductDetailsViewModel::class.java)

        viewModel.getProduct(productId)

        viewModel.getProductLiveD().observe(this, Observer {

            if (it == null) return@Observer
            title = it.name
            titleTv.text = it.name
            priceTv.text = it.price
            descTv.text = it.desc
            productIv.loadUrl(it.image.link)
            productIv.adjustHeight(it.image.height, it.image.width, 32)

        })


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun injectActivity() {
        DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .dbModule(DbModule(application))
            .build()
            .inject(this)
    }

    companion object {
        private const val PRODUCT_ID_KEY = "ProductIdKey"

        fun navigate(context: Context, productId: Int) {
            context.startActivity(
                Intent(context, ProductDetailsActivity::class.java)
                    .putExtra(PRODUCT_ID_KEY, productId)
            )

        }
    }
}
