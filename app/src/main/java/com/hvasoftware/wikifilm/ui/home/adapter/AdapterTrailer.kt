package com.hvasoftware.wikifilm.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.model.MovieVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class AdapterTrailer(
    private val mContext: Context,
    private val itemClickedListener: (MovieVideo) -> Unit
) :
    RecyclerView.Adapter<AdapterTrailer.MyViewHolder>() {

    private var mData: MutableList<MovieVideo> = ArrayList()

    fun setData(data: MutableList<MovieVideo>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_youtube_player, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.rootView.setOnClickListener { itemClickedListener(item) }
        holder.youTubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                // to show full screen but it's not work in adapter
//                val defaultPlayerUiController =
//                    DefaultPlayerUiController(holder.youTubePlayerView, youTubePlayer)
//                holder.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.cueVideo(item.key, 0f)
            }
        })
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: ConstraintLayout = itemView.findViewById(R.id.rootView)
        val youTubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.youTubePlayerView)
    }


}