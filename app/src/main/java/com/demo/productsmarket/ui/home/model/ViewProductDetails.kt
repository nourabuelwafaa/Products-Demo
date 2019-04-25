package com.demo.productsmarket.ui.home.model

import com.demo.productsmarket.data.Image

data class ViewProductDetails(
    val name: String,
    val image: Image,
    val price: String,
    val desc: String
)