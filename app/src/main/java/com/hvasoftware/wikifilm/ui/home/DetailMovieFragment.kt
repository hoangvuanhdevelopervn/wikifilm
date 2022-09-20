package com.hvasoftware.wikifilm.ui.home

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IMovieVideoCallback
import com.hvasoftware.wikifilm.databinding.FragmentDetailMovieBinding
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.response.MovieVideoResponse


class DetailMovieFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private val args: DetailMovieFragmentArgs by navArgs()
    private var movieId = ""
    private var type = ""
    private lateinit var adapterTrailer: AdapterTrailer


    override fun setContentView(): View {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()

        handleIntent()

        setupAdapter()
    }


    override fun onViewClick() {
        binding.headerBar.ivBack.setOnClickListener { onBackPress() }

    }

    override fun loadData() {
        if (type == Constants.FilmType.MOVIE.toString()) {
            loadVideoMovie()
        } else {
            loadVideoTVSeries()
        }
    }

    private fun handleIntent() {
        movieId = args.movieId.toString()
        type = args.type.toString()
    }

    private fun setupAdapter() {
        adapterTrailer = AdapterTrailer(requireContext(), itemClickedListener = {
        })
        binding.rvTrailers.adapter = adapterTrailer
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTrailers)
    }

    private fun loadVideoMovie() {
        movieViewModel.loadVideoMovie(requireContext(), movieId, object : IMovieVideoCallback {
            override fun onSuccess(response: MovieVideoResponse) {
                response.results?.let { adapterTrailer.setData(it) }
            }

            override fun onError(error: VolleyError) {
                error.localizedMessage?.let { logger(it) }
            }
        })
    }

    private fun loadVideoTVSeries() {
        movieViewModel.loadVideoTVSeries(requireContext(), movieId, object : IMovieVideoCallback {
            override fun onSuccess(response: MovieVideoResponse) {
                response.results?.let { adapterTrailer.setData(it) }
            }

            override fun onError(error: VolleyError) {
                error.localizedMessage?.let { logger(it) }
            }
        })
    }


}