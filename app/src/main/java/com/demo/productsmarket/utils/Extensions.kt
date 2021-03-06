package com.demo.productsmarket.utils

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.DisplayMetrics
import android.widget.ImageView

fun Context.dpToPixel(dp: Int): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

}

fun ImageView.loadUrl(url: String) {
    GlideApp.with(this).load(url).into(this)
}


fun ImageView.adjustHeight(height: Int, width: Int, viewMargins: Int) {
    val cellWidth = Resources.getSystem().displayMetrics.widthPixels - context.dpToPixel(viewMargins)
    val imageHeight = cellWidth * height / width
    layoutParams.height = imageHeight.toInt()
}

fun Context.isConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo?

    activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting

}

