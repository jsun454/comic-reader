package com.example.jeffrey.comicreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jeffrey.comicreader.Adapter.MySliderAdapter
import com.example.jeffrey.comicreader.Common.Common
import com.example.jeffrey.comicreader.Retrofit.IComicAPI
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_detail.*

class ViewDetail : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_detail)

        iComicAPI = Common.api

        prev.setOnClickListener {
            if(Common.chapter_index == 0) {
                Toast.makeText(baseContext, "You are reading the first chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapter_index--
                fetchImages(Common.chapter_list[Common.chapter_index].ID)
            }
        }
        next.setOnClickListener {
            if(Common.chapter_index == Common.chapter_list.size - 1) {
                Toast.makeText(baseContext, "You are reading the last chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapter_index++
                fetchImages(Common.chapter_list[Common.chapter_index].ID)
            }
        }

        fetchImages(Common.selected_chapter!!.ID)
    }

    private fun fetchImages(id: Int) {
        val dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please wait...")
            .build()
        dialog.show()
        compositeDisposable.add(iComicAPI.getLinkList(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listLink ->
                val adapter = MySliderAdapter(baseContext, listLink)
                view_pager.adapter = adapter
                text_chapter_name.text = Common.formatString(Common.selected_chapter!!.Name)

                val bookFlipPageTransformer = BookFlipPageTransformer()
                bookFlipPageTransformer.scaleAmountPercent=10f
                view_pager.setPageTransformer(true, bookFlipPageTransformer)

                dialog.dismiss()
            },
                { thr ->
                    Toast.makeText(baseContext, "" + thr.message, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }))
    }
}
