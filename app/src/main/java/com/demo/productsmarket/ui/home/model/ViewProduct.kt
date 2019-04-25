package com.demo.productsmarket.ui.home.model

import com.demo.productsmarket.data.Image

data class ViewProduct(
    val id: Int,
    val name: String,
    val image: Image,
    val price: String
)