package com.android.bestpracticechallenge.ui.tools


import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import com.android.bestpracticechallenge.App


object Tools {
    fun viewVisibility(view: View) {
        if (view.visibility == View.VISIBLE)
            view.visibility = View.INVISIBLE
        else
            view.visibility = View.VISIBLE
    }

    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            App.instance.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }

    fun getScreenDimenss(): Point {
        val context = App.instance.getContext()
        val display: DisplayMetrics? = context.resources.displayMetrics
        return Point(display!!.widthPixels, display.heightPixels)
    }
}