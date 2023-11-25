package com.hvasoftware.wikifilm.ui.actors

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IActorListener
import com.hvasoftware.wikifilm.callback.IActorSocialCallback
import com.hvasoftware.wikifilm.callback.IScrollListener
import com.hvasoftware.wikifilm.data.Actor
import com.hvasoftware.wikifilm.data.response.PopularActorResponse
import com.hvasoftware.wikifilm.databinding.FragmentSearchActorsBinding
import com.hvasoftware.wikifilm.extensions.*
import com.hvasoftware.wikifilm.extensions.toastLong

class SearchActorsFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchActorsBinding
    private lateinit var mAdapterActors: AdapterActors
    private var mCurrentPage = 1
    private var mIsLoadMore = true
    private val mListActors: MutableList<Actor> = arrayListOf()
    private var content = ""


    override fun setContentView(): View {
        binding = FragmentSearchActorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewClick() {
        binding.ibBack.setOnClickListener { onBackPress() }
    }


    override fun initData() {
        super.initData()
        setupAdapter()
        setUpTextSearch()
    }


    override fun loadData() {


    }


    private fun setUpTextSearch() {
        binding.edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                content = binding.edtSearch.text.toString().trim()
                if (content.isNullOrEmpty()) {
                    toastShort("Please input actor name")
                } else {
                    mListActors.clear()
                    mCurrentPage = 1
                    mIsLoadMore = true
                    searchActor()
                }
                return@OnEditorActionListener true
            }
            false
        })
    }


    private fun setupAdapter() {
        mAdapterActors = AdapterActors(requireContext(), itemClickedListener = {
            navigateToFragment(ActorsFragmentDirections.actionToActorDetailFragment(it.id, it.name))
        })
        binding.rvActors.adapter = mAdapterActors
        mAdapterActors.setScroll(object : IScrollListener {
            override fun onScroll(position: Int) {
                if (position == mAdapterActors.itemCount - 1 && mIsLoadMore) {
                    searchActor()
                }
            }
        })
    }

    private fun searchActor() {
        binding.pbLoading.show()
        actorsViewModel.searchActor(
            requireContext(),
            urlEncode(content),
            mCurrentPage,
            object : IActorListener {
                override fun onSuccess(response: PopularActorResponse) {
                    if (response.results.isEmpty() || mCurrentPage > 20) {
                        mIsLoadMore = false
                        toastShort("No Data")
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