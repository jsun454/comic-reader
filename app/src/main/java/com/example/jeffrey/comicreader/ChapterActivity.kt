package com.example.jeffrey.comicreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.jeffrey.comicreader.Adapter.MyChapterAdapter
import com.example.jeffrey.comicreader.Common.Common
import com.example.jeffrey.comicreader.Retrofit.IComicAPI
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chapter.*

class ChapterActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        iComicAPI = Common.api

        toolbar.title = Common.selected_comic!!.Name
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        recycler_chapter.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recycler_chapter.layoutManager = layoutManager
        recycler_chapter.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        fetchChapter(Common.selected_comic!!.ID)
    }

    private fun fetchChapter(id: Int) {
        val dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please wait...")
            .build()
        dialog.show()
        compositeDisposable.add(iComicAPI.getChapterList(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ chapterList ->
                text_chapter.text = StringBuilder("CHAPTER (")
                    .append(chapterList.size)
                    .append(")")

                recycler_chapter.adapter = MyChapterAdapter(baseContext, chapterList)
                Common.chapter_list = chapterList
                dialog.dismiss()
            },
                { thr ->
                    Toast.makeText(baseContext, "" + thr.message, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }))
    }
}
