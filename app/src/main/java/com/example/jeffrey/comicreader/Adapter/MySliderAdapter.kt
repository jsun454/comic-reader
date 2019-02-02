package com.example.jeffrey.comicreader.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jeffrey.comicreader.Model.Link
import com.example.jeffrey.comicreader.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class MySliderAdapter(internal var context: Context, internal var linkList: List<Link>): PagerAdapter() {

    internal var inflater: LayoutInflater
    init {
        inflater = LayoutInflater.from(context)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return linkList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image_layout = inflater.inflate(R.layout.viewpager_item, container, false)
        val photo_view = image_layout.findViewById(R.id.page_image) as PhotoView
        Picasso.get().load(linkList[position].Link).into(photo_view)
        container.addView(image_layout)
        return image_layout
    }
}