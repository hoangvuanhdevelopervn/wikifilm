package com.hvasoftware.wikifilm.ui.actors

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.callback.IScrollListener
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.data.Actor


class AdapterActors(
    private val mContext: Context,
    private val itemClickedListener: (Actor) -> Unit
) :
    RecyclerView.Adapter<AdapterActors.MyViewHolder>() {

    private var mData: MutableList<Actor> = ArrayList()
    private lateinit var iScrollListener: IScrollListener

    fun setData(data: MutableList<Actor>) {
        this.mData = data
        notifyDataSetChanged()
    }

    fun setScroll(scrollListener: IScrollListener) {
        this.iScrollListener = scrollListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_actors, parent, false)
        return MyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        var knowFor = ""
        holder.ivActor.setUrl("${Constants.BASE_URL_IMAGE}${item.profile_path}")
        holder.tvActorName.text = item.name
        if (item.known_for.isEmpty()) {
            holder.tvActorDes.text = item.known_for_department
        } else {
            item.known_for.forEach {
                knowFor += if (it.title != null) {
                    "${it.title}, "
                } else {
                    "${it.name}, "
                }
            }
            holder.tvActorDes.text = knowFor.substring(0, knowFor.length - 2)
        }
        iScrollListener.onScroll(position)
        holder.rootView.setOnClickListener {
            itemClickedListener(item)
        }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: CardView = itemView.findViewById(R.id.rootView)
        val ivActor: ImageView = itemView.findViewById(R.id.ivActor)
        val tvActorName: TextView = itemView.findViewById(R.id.tvActorName)
        val tvActorDes: TextView = itemView.findViewById(R.id.tvActorDes)
    }


}