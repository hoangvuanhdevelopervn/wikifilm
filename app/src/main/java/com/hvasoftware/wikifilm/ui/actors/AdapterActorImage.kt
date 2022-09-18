package com.hvasoftware.wikifilm.ui.actors

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
import com.hvasoftware.wikifilm.model.ActorImage


class AdapterActorImage(
    private val mContext: Context,
    private val itemClickedListener: (ActorImage) -> Unit
) :
    RecyclerView.Adapter<AdapterActorImage.MyViewHolder>() {

    private var mData: MutableList<ActorImage> = ArrayList()

    fun setData(data: MutableList<ActorImage>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_actor_image, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.ivActor.setUrl("${Constants.BASE_URL_IMAGE}${item.file_path}")
        holder.rootView.setOnClickListener {
            itemClickedListener(item)
        }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: ConstraintLayout = itemView.findViewById(R.id.rootView)
        val ivActor: ImageView = itemView.findViewById(R.id.ivActor)
    }


}