// Code is based off an online tutorial
//
// TO START BACKEND:
// cd ComicReader/app/src/main/assets
// node index.js

package com.example.jeffrey.comicreader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.example.jeffrey.comicreader.Adapter.MainSliderAdapter
import com.example.jeffrey.comicreader.Adapter.MyComicAdapter
import com.example.jeffrey.comicreader.Common.Common
import com.example.jeffrey.comicreader.Retrofit.IComicAPI
import com.example.jeffrey.comicreader.Service.PicassoImageLoadingService
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider

class MainActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iComicAPI = Common.api

        Slider.init(PicassoImageLoadingService(this))

        button_search.setOnClickListener { startActivity(Intent(this@MainActivity, CategoryFilter::class.java)) }

        recycler_comic.setHasFixedSize(true)
        recycler_comic.layoutManager = GridLayoutManager(this, 2)

        swipe_refresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_orange_dark, android.R.color.background_dark)
        swipe_refresh.setOnRefreshListener {
            if(Common.isConnectedToInternet(baseContext)) {
                fetchBanner()
                fetchComic()
            } else {
                Toast.makeText(baseContext, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
        swipe_refresh.post {
            if(Common.isConnectedToInternet(baseContext)) {
                fetchBanner()
                fetchComic()
            } else {
                Toast.makeText(baseContext, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchBanner() {
        compositeDisposable.add(iComicAPI.bannerList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                banners -> banner_slider.setAdapter(MainSliderAdapter(banners))
            },
                {
                    Toast.makeText(baseContext, "Error while loading banner", Toast.LENGTH_SHORT).show()
                }))
    }

    private fun fetchComic() {
        val dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please wait...")
            .build()
        if(!swipe_refresh.isRefreshing) {
            dialog.show()
        }
        compositeDisposable.add(iComicAPI.comicList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ comicList ->
                    text_comic.text = StringBuilder("NEW COMIC (")
                        .append(comicList.size)
                        .append(")")
                    recycler_comic.adapter = MyComicAdapter(baseContext, comicList)
                if(!swipe_refresh.isRefreshing) {
                    dialog.dismiss()
                }
                swipe_refresh.isRefreshing = false
            },
                { thr ->
                    Toast.makeText(baseContext, "" + thr.message, Toast.LENGTH_SHORT).show()
                    if(!swipe_refresh.isRefreshing) {
                        dialog.dismiss()
                    }
                    swipe_refresh.isRefreshing = false
                }))
    }
}
