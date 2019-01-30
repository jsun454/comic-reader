package com.example.jeffrey.comicreader.Retrofit

import com.example.jeffrey.comicreader.Model.Banner
import com.example.jeffrey.comicreader.Model.Comic
import io.reactivex.Observable
import retrofit2.http.GET

interface IComicAPI {
    @get:GET("banner")
    val bannerList: Observable<List<Banner>>
    @get:GET("comic")
    val comicList: Observable<List<Comic>>
}