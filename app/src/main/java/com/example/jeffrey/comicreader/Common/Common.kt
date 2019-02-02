package com.example.jeffrey.comicreader.Common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.jeffrey.comicreader.Model.Category
import com.example.jeffrey.comicreader.Model.Chapter
import com.example.jeffrey.comicreader.Model.Comic
import com.example.jeffrey.comicreader.Retrofit.IComicAPI
import com.example.jeffrey.comicreader.Retrofit.RetrofitClient

object Common {

    var selected_comic: Comic? = null
    var chapter_list : List<Chapter> = ArrayList()
    var selected_chapter: Chapter? = null
    var chapter_index: Int = -1
    var categories: List<Category>? = ArrayList()

    fun isConnectedToInternet(context: Context?): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT < 23) {
            val ni = cm.activeNetworkInfo
            if(ni != null) {
                @Suppress("DEPRECATION")
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n = cm.activeNetwork
            if(n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
        return false
    }

    fun formatString(name: String?): CharSequence? {
        val finalResult = StringBuilder(if (name!!.length > 15) name.substring(0, 15) + "..." else name)
        return finalResult.toString()
    }

    val api: IComicAPI
    get() {
        val retrofit = RetrofitClient.instance
        return retrofit.create(IComicAPI::class.java)
    }
}