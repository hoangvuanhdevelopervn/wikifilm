package com.hvasoftware.wikifilm.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.extensions.convertDate
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.Movie


class AdapterTrending(private val mContext: Context) :
    RecyclerView.Adapter<AdapterTrending.MyViewHolder>() {

    private var mData: MutableList<Movie> = ArrayList()

    fun setData(data: MutableList<Movie>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_movies_trending, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.ivMovie.setUrl("${Constants.BASE_URL_IMAGE}${item.poster_path}")
        holder.tvRating.text = "${item.vote_average}"
        if (item.title != null) {
            holder.tvMovieName.text = item.title
        } else if (item.original_title != null) {
            holder.tvMovieName.text = item.original_title
        } else {
            holder.tvMovieName.text = item.original_name
        }
        if (item.release_date != null && item.release_date.isNotEmpty()) {
            holder.tvMovieDes.text = convertDate(item.release_date)
        } else if (item.first_air_date != null && item.first_air_date.isNotEmpty()) {
            holder.tvMovieDes.text = convertDate(item.first_air_date)
        }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMovie: ImageView = itemView.findViewById(R.id.ivMovie)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val tvMovieName: TextView = itemView.findViewById(R.id.tvMovieName)
        val tvMovieDes: TextView = itemView.findViewById(R.id.tvMovieDes)
    }


}