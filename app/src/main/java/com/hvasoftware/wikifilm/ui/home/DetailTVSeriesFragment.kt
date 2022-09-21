package com.hvasoftware.wikifilm.ui.home

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IMovieVideoCallback
import com.hvasoftware.wikifilm.databinding.FragmentDetailTvSeriesBinding
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.model.response.MovieVideoResponse
import com.hvasoftware.wikifilm.ui.home.adapter.AdapterTrailer

class DetailTVSeriesFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailTvSeriesBinding
    private val args: DetailTVSeriesFragmentArgs by navArgs()
    private var tvId = ""
    private lateinit var adapterTrailer: AdapterTrailer


    override fun setContentView(): View {
        binding = FragmentDetailTvSeriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()

        handleIntent()

        setupAdapter()
    }


    private fun handleIntent() {
        tvId = args.tvId.toString()
    }

    private fun setupAdapter() {
        adapterTrailer = AdapterTrailer(requireContext(), itemClickedListener = {
        })
        binding.rvTrailers.adapter = adapterTrailer
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTrailers)
    }

    override fun onViewClick() {
    }

    override fun loadData() {

        loadVideoTVSeries()

    }

    private fun loadVideoTVSeries() {
        movieViewModel.loadVideoTVSeries(requireContext(), tvId, object : IMovieVideoCallback {
            override fun onSuccess(response: MovieVideoResponse) {
                response.results?.let { adapterTrailer.setData(it) }
            }
            override fun onError(error: VolleyError) {
                error.localizedMessage?.let { logger(it) }
            }
        })
    }



}