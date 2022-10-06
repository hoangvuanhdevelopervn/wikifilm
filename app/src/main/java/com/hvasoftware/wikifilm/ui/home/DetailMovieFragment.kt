package com.hvasoftware.wikifilm.ui.home

import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.base.BaseResponse
import com.hvasoftware.wikifilm.callback.IMovieVideoCallback
import com.hvasoftware.wikifilm.data.Movie
import com.hvasoftware.wikifilm.data.response.MovieVideoResponse
import com.hvasoftware.wikifilm.databinding.FragmentDetailMovieBinding
import com.hvasoftware.wikifilm.extensions.convertDate
import com.hvasoftware.wikifilm.extensions.convertMovieTime
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.ui.home.adapter.AdapterTrailer


class DetailMovieFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private val args: DetailMovieFragmentArgs by navArgs()
    private var movieId = ""
    private lateinit var adapterTrailer: AdapterTrailer
    private lateinit var adapterGenre: AdapterGenre


    override fun setContentView(): View {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()
        binding.tvMovieDes.movementMethod = ScrollingMovementMethod()


        handleIntent()

        setupAdapter()

        setupAdapterGenre()
    }


    override fun onViewClick() {
        binding.headerBar.ivBack.setOnClickListener { onBackPress() }

    }

    override fun loadData() {
        loadVideoMovie()

        loadDetailMovie()

    }

    private fun handleIntent() {
        movieId = args.movieId.toString()
    }

    private fun setupAdapter() {
        adapterTrailer = AdapterTrailer(requireContext(), itemClickedListener = {
        })
        binding.rvTrailers.adapter = adapterTrailer
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTrailers)
    }

    private fun setupAdapterGenre() {
        adapterGenre = AdapterGenre(requireContext(), itemClickedListener = {
        })
        binding.rvMovieGenre.adapter = adapterGenre
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

    private fun loadDetailMovie() {
        movieViewModel.loadDetailMovie(requireContext(), movieId)
        movieViewModel.loadDetailMovieState.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    val data = it.data
                    dummyData(movie = data)
                }
                is BaseResponse.Error -> {
                    it.serverError?.localizedMessage?.let { it1 -> logger(it1) }
                }
                else -> {}
            }
        }
    }


    private fun dummyData(movie: Movie) {
        binding.headerBar.tvTitle.text = movie.title
        binding.tvMovieName.text = movie.title
        binding.tvMovieInfo.text =
            "${movie.status} - ${convertDate(movie.release_date)} - ${convertMovieTime(movie.runtime)}"
        binding.sivMovie.setUrl("${Constants.BASE_URL_IMAGE}${movie.poster_path}")
        binding.tvMovieDes.text = movie.overview
        adapterGenre.setData(movie.genres)
    }


}