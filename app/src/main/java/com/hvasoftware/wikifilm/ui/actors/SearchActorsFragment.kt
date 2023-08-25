package com.hvasoftware.wikifilm.ui.actors

import android.view.View
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.data.Actor
import com.hvasoftware.wikifilm.databinding.FragmentSearchActorsBinding

class SearchActorsFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchActorsBinding
    private lateinit var mAdapterActors: AdapterActors
    private var mCurrentPage = 1
    private var mIsLoadMore = true
    private val mListActors: MutableList<Actor> = arrayListOf()


    override fun setContentView(): View {
        binding = FragmentSearchActorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewClick() {
        binding.ibBack.setOnClickListener { onBackPress() }
    }

    override fun loadData() {
    }

}