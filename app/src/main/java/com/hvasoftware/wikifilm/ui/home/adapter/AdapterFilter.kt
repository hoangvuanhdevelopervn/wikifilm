package com.hvasoftware.wikifilm.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.extensions.getFilterName
import com.hvasoftware.wikifilm.model.FilterType


class AdapterFilter(
    private val mContext: Context,
    private val isSingle: Boolean,
    private val itemClickedListener: (FilterType) -> Unit
) :
    RecyclerView.Adapter<AdapterFilter.MyViewHolder>() {

    private var mData: MutableList<FilterType> = ArrayList()

    fun setData(data: MutableList<FilterType>) {
        this.mData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_filter_type, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mData[position]
        holder.tvFilterType.text = getFilterName(item.type)
        if (item.isSelected) {
            holder.tvFilterType.setBackgroundResource(R.drawable.bg_view_small_type_selected)
        } else {
            holder.tvFilterType.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        holder.tvFilterType.setOnClickListener {
            itemClickedListener(item)
            if (isSingle)
                removeAll(position)
        }

    }

    private fun removeAll(position: Int) {
        mData.forEach {
            it.isSelected = false
        }
        mData[position].isSelected = true
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFilterType: TextView = itemView.findViewById(R.id.tvFilterType)
    }


}