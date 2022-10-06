package com.hvasoftware.wikifilm.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.data.Genre


class AdapterGenre(
    private val mContext: Context,
    private val itemClickedListener: (Genre) -> Unit
) :
    RecyclerView.Adapter<AdapterGenre.MyViewHolder>() {

    private var mData: MutableList<Genre> = ArrayList()

    fun setData(data: MutableList<Genre>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_movie_genre, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.tvMovieGenre.text = item.name
        holder.tvMovieGenre.setOnClickListener { itemClickedListener(item) }

    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMovieGenre: TextView = itemView.findViewById(R.id.tvMovieGenre)
    }


}