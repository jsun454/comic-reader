package com.example.jeffrey.comicreader.Retrofit

import com.example.jeffrey.comicreader.Model.Banner
import com.example.jeffrey.comicreader.Model.Chapter
import com.example.jeffrey.comicreader.Model.Comic
import com.example.jeffrey.comicreader.Model.Link
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface IComicAPI {
    @get:GET("banner")
    val bannerList: Observable<List<Banner>>
    @get:GET("comic")
    val comicList: Observable<List<Comic>>

    @GET("chapter/{mangaid}")
    fun getChapterList(@Path("mangaid") mangaId: Int): Observable<List<Chapter>>
    @GET("links/{chapterid}")
    fun getLinkList(@Path("chapterid") mangaId: Int): Observable<List<Link>>
}