package com.hvasoftware.wikifilm.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.data.Movie


class AdapterUpcoming(
    private val mContext: Context,
    private val itemClickedListener: (Movie) -> Unit
) :
    RecyclerView.Adapter<AdapterUpcoming.MyViewHolder>() {

    private var mData: MutableList<Movie> = ArrayList()

    fun setData(data: MutableList<Movie>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_movie_upcoming, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.ivMovie.setUrl("${Constants.BASE_URL_IMAGE}${item.backdrop_path}")
        holder.rootView.setOnClickListener { itemClickedListener(item) }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: ConstraintLayout = itemView.findViewById(R.id.rootView)
        val ivMovie: ImageView = itemView.findViewById(R.id.ivMovie)
    }


}