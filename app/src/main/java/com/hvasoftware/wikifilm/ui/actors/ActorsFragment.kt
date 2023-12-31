package com.hvasoftware.wikifilm.ui.actors

import android.view.View
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IActorListener
import com.hvasoftware.wikifilm.callback.IScrollListener
import com.hvasoftware.wikifilm.data.Actor
import com.hvasoftware.wikifilm.data.response.PopularActorResponse
import com.hvasoftware.wikifilm.databinding.FragmentActorsBinding
import com.hvasoftware.wikifilm.extensions.hide
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.extensions.toastLong

class ActorsFragment : BaseFragment() {

    private lateinit var binding: FragmentActorsBinding
    private lateinit var mAdapterActors: AdapterActors
    private var mCurrentPage = 1
    private var mIsLoadMore = true
    private val mListActors: MutableList<Actor> = arrayListOf()


    override fun setContentView(): View {
        binding = FragmentActorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()

        setupAdapter()

    }

    override fun loadData() {
        if (mListActors.isEmpty()) {
            loadListActors()
        } else {
            binding.pbLoading.hide()
            mAdapterActors.setData(mListActors)
        }

    }


    override fun onViewClick() {

        binding.ibSearch.setOnClickListener {
            navigateToFragment(ActorsFragmentDirections.actionToSearchActorsFragment())
        }
    }

    private fun setupAdapter() {
        mAdapterActors = AdapterActors(requireContext(), itemClickedListener = {
            navigateToFragment(ActorsFragmentDirections.actionToActorDetailFragment(it.id, it.name))
        })
        binding.rvActors.adapter = mAdapterActors
        mAdapterActors.setScroll(object : IScrollListener {
            override fun onScroll(position: Int) {
                if (position == mAdapterActors.itemCount - 1 && mIsLoadMore) {
                    loadListActors()
                }
            }
        })
    }

    private fun loadListActors() {
        actorsViewModel.loadListActors(requireContext(), mCurrentPage, object : IActorListener {
            override fun onSuccess(response: PopularActorResponse) {
                if (response.results.isEmpty() || mCurrentPage > 20) {
                    mIsLoadMore = false
                } else {
                    mListActors.addAll(response.results)
                    mAdapterActors.setData(mListActors)
                    mCurrentPage += 1
                }
                binding.pbLoading.hide()
            }

            override fun onError(error: VolleyError) {
                error.localizedMessage?.let { toastLong(it) }
                mIsLoadMore = false
                binding.pbLoading.hide()
            }
        })
    }



}