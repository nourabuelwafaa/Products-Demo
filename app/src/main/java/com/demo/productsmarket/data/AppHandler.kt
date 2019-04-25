package com.demo.productsmarket.data

import android.os.Build
import android.os.Handler
import android.os.HandlerThread

/**
 * This class is used by Repositories for making heavy operations
 */
class AppHandler {

    private val handlerThread: HandlerThread = HandlerThread("MyHandlerThread")
    private val handler: Handler


    init {
        handlerThread.start()
        val looper = handlerThread.looper
        handler = Handler(looper)
    }

    fun post(r: () -> Unit) {
        handler.post(r)
    }

    fun quit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            handlerThread.quitSafely()
        } else {
            handlerThread.quit()
        }
    }

}