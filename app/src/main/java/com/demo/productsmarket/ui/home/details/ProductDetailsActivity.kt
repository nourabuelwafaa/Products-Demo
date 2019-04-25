package com.demo.productsmarket.ui.home.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.demo.productsmarket.R
import com.demo.productsmarket.di.ApiModule
import com.demo.productsmarket.di.AppModule
import com.demo.productsmarket.di.DaggerAppComponent
import com.demo.productsmarket.di.DbModule
import com.demo.productsmarket.utils.adjustHeight
import com.demo.productsmarket.utils.dpToPixel
import com.demo.productsmarket.utils.loadUrl
import javax.inject.Inject
import kotlin.math.abs

class ProductDetailsActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        injectActivity()

        val collapsingLayout: CollapsingToolbarLayout = findViewById(R.id.collapsingToolbar)
        val mainView: View = findViewById(R.id.mainView)

        collapsingLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))
        collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.transparent))

        val appbar: AppBarLayout = findViewById(R.id.appBar)

        appbar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { view, verticalOffset ->

                calculateNewPadding(verticalOffset, view, mainView)

            })

        val titleTv: TextView = findViewById(R.id.titleTv)
        val priceTv: TextView = findViewById(R.id.priceTv)
        val descTv: TextView = findViewById(R.id.descTv)
        val productIv: ImageView = findViewById(R.id.productIv)

        val productId = intent.getIntExtra(PRODUCT_ID_KEY, 0)

        viewModel = ViewModelProviders.of(this, factory).get(ProductDetailsViewModel::class.java)

        viewModel.getProduct(productId)

        viewModel.getProductLiveD().observe(this, Observer {

            if (it == null) return@Observer
            titleTv.text = it.name
            priceTv.text = it.price
            descTv.text = it.desc
            productIv.loadUrl(it.image.link)
            productIv.adjustHeight(it.image.height, it.image.width, viewMargins = 0)

        })


    }

    /**
    Function for calculating new padding value for the content view
    after each new scrolling value
     */
    private fun calculateNewPadding(
        verticalOffset: Int,
        view: AppBarLayout,
        mainView: View
    ) {
        val maxPadding = 24
        val absValue = abs(verticalOffset).toFloat()
        val maxScrollRange = view.totalScrollRange.toFloat()
        val percentage = 1 - absValue / maxScrollRange
        val currentPadValueInPixels = dpToPixel((percentage * maxPadding).toInt())
        mainView.setPadding(0, currentPadValueInPixels.toInt(), 0, 0)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun injectActivity() {
        DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .appModule(AppModule(application))
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
