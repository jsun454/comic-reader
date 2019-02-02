package com.example.jeffrey.comicreader.Retrofit

import com.example.jeffrey.comicreader.Model.*
import io.reactivex.Observable
import retrofit2.http.*

interface IComicAPI {
    @get:GET("banner")
    val bannerList: Observable<List<Banner>>
    @get:GET("comic")
    val comicList: Observable<List<Comic>>
    @get:GET("categories")
    val categoryList: Observable<List<Category>>

    @GET("chapter/{mangaid}")
    fun getChapterList(@Path("mangaid") mangaId: Int): Observable<List<Chapter>>
    @GET("links/{chapterid}")
    fun getLinkList(@Path("chapterid") mangaId: Int): Observable<List<Link>>

    @POST("filter")
    @FormUrlEncoded
    fun getFilteredComic(@Field("data") data: String): Observable<List<Comic>>

    @POST("search")
    @FormUrlEncoded
    fun getSearchedComic(@Field("search") data: String): Observable<List<Comic>>
}