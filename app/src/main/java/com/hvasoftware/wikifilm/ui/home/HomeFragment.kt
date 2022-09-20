package com.hvasoftware.wikifilm.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IMovieTrendingCallback
import com.hvasoftware.wikifilm.callback.IMovieUpcomingCallback
import com.hvasoftware.wikifilm.databinding.FragmentHomeBinding
import com.hvasoftware.wikifilm.extensions.getCurrentPosition
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.extensions.toastLong
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.Movie
import com.hvasoftware.wikifilm.model.response.TrendingResponse
import com.hvasoftware.wikifilm.model.response.UpcomingMovieResponse

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterTrending: AdapterTrending
    private lateinit var adapterUpcoming: AdapterUpcoming
    private val mListMovieUpcoming: MutableList<Movie> = arrayListOf()


    override fun setContentView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()

        setupAdapterUpcoming()

        setupAdapter()

    }

    override fun loadData() {

        loadListMovieUpcoming()

        loadListMovieTrending(Constants.MediaType.ALL)


    }

    override fun onViewClick() {
        binding.typeAll.setOnClickListener {
            loadListMovieTrending(Constants.MediaType.ALL)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeTVSeries.setOnClickListener {
            loadListMovieTrending(Constants.MediaType.TV)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeMovie.setOnClickListener {
            loadListMovieTrending(Constants.MediaType.MOVIE)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type_selected)
        }
    }

    private fun setupAdapterUpcoming() {
        adapterUpcoming = AdapterUpcoming(requireContext(), itemClickedListener = {
            navigateToFragment(
                HomeFragmentDirections.actionToDetailMovieFragment(
                    it.id,
                    Constants.FilmType.MOVIE.toString()
                )
            )
        })
        binding.rvUpcoming.adapter = adapterUpcoming
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvUpcoming)
        binding.rvUpcoming.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val mCurrentPosition = binding.rvUpcoming.getCurrentPosition()
                    setupMovieUpcoming(mCurrentPosition)
                }
            }
        })
    }

    private fun setupAdapter() {
        adapterTrending = AdapterTrending(requireContext(), itemClickedListener = {
            if (it.media_type == "movie") {
                navigateToFragment(
                    HomeFragmentDirections.actionToDetailMovieFragment(
                        it.id,
                        Constants.FilmType.MOVIE.toString()
                    )
                )
            } else {
                navigateToFragment(
                    HomeFragmentDirections.actionToDetailMovieFragment(
                        it.id,
                        Constants.FilmType.TV.toString()
                    )
                )
            }
        });
        binding.rvTrending.adapter = adapterTrending
    }

    private fun loadListMovieUpcoming() {
        viewModel.loadListMovieUpcoming(
            requireContext(),
            1,
            object : IMovieUpcomingCallback {
                override fun onSuccess(response: UpcomingMovieResponse) {
                    mListMovieUpcoming.clear()
                    mListMovieUpcoming.addAll(response.results)
                    adapterUpcoming.setData(mListMovieUpcoming)
                    setupMovieUpcoming(0)
                }

                override fun onError(error: VolleyError) {
                    error.localizedMessage?.let { toastLong(it) }
                }
            })
    }

    private fun setupMovieUpcoming(position: Int) {
        val movie = mListMovieUpcoming[position]
        binding.ivMovieUpcoming.setUrl("${Constants.BASE_URL_IMAGE}${movie.poster_path}")
        if (movie.title != null) {
            binding.tvMovieName.text = movie.title
        } else {
            binding.tvMovieName.text = movie.original_title
        }
        binding.tvMovieInfo.text = movie.overview
        binding.tvNumber.text = "${position + 1}/${mListMovieUpcoming.size}"
    }

    private fun loadListMovieTrending(type: Constants.MediaType) {
        viewModel.loadListMovieTrending(
            requireContext(),
            type.toString().lowercase(),
            Constants.TimeWindow.WEEK.toString().lowercase(),
            object : IMovieTrendingCallback {
                override fun onSuccess(response: TrendingResponse) {
                    adapterTrending.setData(response.results)
                }

                override fun onError(error: VolleyError) {
                    error.localizedMessage?.let { toastLong(it) }
                }
            })
    }


}