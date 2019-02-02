package com.example.jeffrey.comicreader.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.jeffrey.comicreader.ChapterActivity
import com.example.jeffrey.comicreader.Common.Common
import com.example.jeffrey.comicreader.Interface.IRecyclerOnClick
import com.example.jeffrey.comicreader.Model.Comic
import com.example.jeffrey.comicreader.R
import com.squareup.picasso.Picasso

class MyComicAdapter(internal var context: Context, internal var mangaList: List<Comic>): RecyclerView.Adapter<MyComicAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.comic_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(mangaList[position].Image).into(holder.comic_image)
        holder.comic_name.text = mangaList[position].Name
        holder.setClickListener(object: IRecyclerOnClick {
            override fun onClick(view: View, position: Int) {
                Common.selected_comic = mangaList[position]
                context.startActivity(Intent(context, ChapterActivity::class.java))
            }
        })
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var comic_image: ImageView
        internal var comic_name: TextView
        lateinit var iRecyclerOnClick: IRecyclerOnClick

        fun setClickListener(iRecyclerOnClick: IRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick
        }

        init {
            comic_image = itemView.findViewById(R.id.comic_image) as ImageView
            comic_name = itemView.findViewById(R.id.comic_name) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            iRecyclerOnClick.onClick(v!!, adapterPosition)
        }
    }

}