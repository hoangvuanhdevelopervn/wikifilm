package com.hvasoftware.wikifilm.ui.actors

import android.view.View
import androidx.fragment.app.viewModels
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IActorListener
import com.hvasoftware.wikifilm.callback.IScrollListener
import com.hvasoftware.wikifilm.databinding.FragmentActorsBinding
import com.hvasoftware.wikifilm.extensions.hide
import com.hvasoftware.wikifilm.extensions.toastLong
import com.hvasoftware.wikifilm.model.Actor
import com.hvasoftware.wikifilm.model.PopularActorResponse

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

        loadListActors()

    }


    override fun onViewClick() {

    }

    private fun setupAdapter() {
        mAdapterActors = AdapterActors(requireContext(), itemClickedListener = {
            navigateToFragment(ActorsFragmentDirections.actionToActorDetailFragment(it.id, it.name))
        })
        binding.rvActors.adapter = mAdapterActors
        mAdapterActors.setScroll(object : IScrollListener {
            override fun onScroll(position: Int) {
                if (position == mAdapterActors.itemCount - 1 && mIsLoadMore) {
                    loadData()
                }
            }
        })
    }

    private fun loadListActors() {
        actorsViewModel.loadListActors(requireContext(), mCurrentPage, object : IActorListener {
            override fun onSuccess(response: PopularActorResponse) {
                binding.pbLoading.hide()
                if (response.results.isEmpty() || mCurrentPage > 20) {
                    mIsLoadMore = false
                } else {
                    mListActors.addAll(response.results)
                    mAdapterActors.setData(mListActors)
                    mCurrentPage += 1
                }
            }

            override fun onError(error: VolleyError) {
                binding.pbLoading.hide()
                error.localizedMessage?.let { toastLong(it) }
                mIsLoadMore = false
            }
        })
    }



}