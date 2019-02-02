package com.example.jeffrey.comicreader.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jeffrey.comicreader.Common.Common
import com.example.jeffrey.comicreader.Interface.IRecyclerOnClick
import com.example.jeffrey.comicreader.Model.Chapter
import com.example.jeffrey.comicreader.R
import com.example.jeffrey.comicreader.ViewDetail

class MyChapterAdapter(internal var context: Context, internal var chapterList: List<Chapter>): RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.chapter_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text_chapter_name.text = chapterList[position].Name
        holder.setClick(object: IRecyclerOnClick {
            override fun onClick(view: View, position: Int) {
                Common.selected_chapter = chapterList[position]
                Common.chapter_index = position
                context.startActivity(Intent(context, ViewDetail::class.java))
            }
        })
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            iRecyclerOnClick.onClick(v!!, adapterPosition)
        }

        internal var text_chapter_name: TextView
        internal lateinit var iRecyclerOnClick: IRecyclerOnClick

        fun setClick(iRecyclerOnClick: IRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick
        }

        init {
            text_chapter_name = itemView.findViewById(R.id.text_chapter_number) as TextView
            itemView.setOnClickListener(this)
        }
    }

}