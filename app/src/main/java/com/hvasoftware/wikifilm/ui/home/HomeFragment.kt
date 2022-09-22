package com.hvasoftware.wikifilm.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.base.BaseResponse
import com.hvasoftware.wikifilm.callback.IMovieTrendingCallback
import com.hvasoftware.wikifilm.data.FilterType
import com.hvasoftware.wikifilm.data.Movie
import com.hvasoftware.wikifilm.data.response.TrendingResponse
import com.hvasoftware.wikifilm.databinding.FragmentHomeBinding
import com.hvasoftware.wikifilm.extensions.getCurrentPosition
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.extensions.setUrl
import com.hvasoftware.wikifilm.extensions.toastLong
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.ui.home.adapter.*

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterTrending: AdapterTrending
    private lateinit var adapterUpcoming: AdapterUpcoming
    private val mListMovieUpcoming: MutableList<Movie> = arrayListOf()
    private lateinit var adapterFilter: AdapterFilter
    private lateinit var adapterMovie: AdapterMovie
    private lateinit var adapterTVSeries: AdapterTVSeries


    override fun setContentView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()

        setupAdapterUpcoming()

        setupAdapterFilterTrending()

        setupAdapterTrending()

        setupAdapterFilterMovie()

        setupAdapterFilterTVSeries()

    }

    override fun loadData() {

        loadListMovies("upcoming")

        loadListMovieTrending(Constants.MediaType.ALL)

        loadListMovies("now_playing")

        loadListTVSeries("airing_today")

    }

    override fun onViewClick() {}

    private fun setupAdapterUpcoming() {
        adapterUpcoming = AdapterUpcoming(requireContext(), itemClickedListener = {
            navigateToFragment(HomeFragmentDirections.actionToDetailMovieFragment(it.id))
            val navController: NavController =
                requireActivity().findNavController(R.id.nav_tab_home)
            navController.run {
                popBackStack()
                navigate(R.id.detailMovieFragment)
            }
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

    private fun setupAdapterFilterTrending() {
        val listFilterMovie: MutableList<FilterType> = arrayListOf()
        listFilterMovie.add(FilterType(Constants.FilterType.ALL, true))
        listFilterMovie.add(FilterType(Constants.FilterType.TV_SERIES, false))
        listFilterMovie.add(FilterType(Constants.FilterType.MOVIE, false))
        adapterFilter = AdapterFilter(requireContext(), true, itemClickedListener = {
            when (it.type) {
                Constants.FilterType.ALL -> {
                    loadListMovieTrending(Constants.MediaType.ALL)
                }
                Constants.FilterType.TV_SERIES -> {
                    loadListMovieTrending(Constants.MediaType.TV)
                }
                else -> {
                    loadListMovieTrending(Constants.MediaType.MOVIE)
                }
            }
        })
        binding.rvFilterTrending.adapter = adapterFilter
        adapterFilter.setData(listFilterMovie)
    }


    private fun setupAdapterTrending() {
        adapterTrending = AdapterTrending(requireContext(), itemClickedListener = {
            if (it.media_type == "movie") {
                navigateToFragment(
                    HomeFragmentDirections.actionToDetailMovieFragment(
                        it.id
                    )
                )
            } else {
                navigateToFragment(
                    HomeFragmentDirections.actionToDetailTVSeriesFragment(
                        it.id
                    )
                )
            }
        });
        binding.rvTrending.adapter = adapterTrending
    }


    private fun loadListMovieTrending(type: Constants.MediaType) {
        viewModel.loadListMovieTrending(
            requireContext(),
            type.toString().lowercase(),
            Constants.TimeWindow.WEEK.toString().lowercase()
        )
        viewModel.loadListMovieTrendingState.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    adapterTrending.setData(it.data.results)
                }
                is BaseResponse.Error -> {
                    it.serverError?.localizedMessage?.let { it1 -> logger(it1) }
                }
                else -> {}
            }
        }
    }

    private fun setupAdapterFilterMovie() {
        val listFilterMovie: MutableList<FilterType> = arrayListOf()
        listFilterMovie.add(FilterType(Constants.FilterType.NOW_PLAYING, true))
        listFilterMovie.add(FilterType(Constants.FilterType.POPULAR, false))
        listFilterMovie.add(FilterType(Constants.FilterType.TOP_RATED, false))
        adapterFilter = AdapterFilter(requireContext(), true, itemClickedListener = {
            when (it.type) {
                Constants.FilterType.NOW_PLAYING -> {
                    loadListMovies("now_playing")
                }
                Constants.FilterType.POPULAR -> {
                    loadListMovies("popular")
                }
                else -> {
                    loadListMovies("top_rated")
                }
            }
        })
        binding.rvFilterMovie.adapter = adapterFilter
        adapterFilter.setData(listFilterMovie)
        adapterMovie = AdapterMovie(requireContext(), itemClickedListener = {
            navigateToFragment(
                HomeFragmentDirections.actionToDetailMovieFragment(
                    it.id
                )
            )
        })
        binding.rvMovies.adapter = adapterMovie
    }

    private fun setupAdapterFilterTVSeries() {
        val listFilterMovie: MutableList<FilterType> = arrayListOf()
        listFilterMovie.add(FilterType(Constants.FilterType.AIRING_TODAY, true))
        listFilterMovie.add(FilterType(Constants.FilterType.ON_AIR, false))
        listFilterMovie.add(FilterType(Constants.FilterType.POPULAR, false))
        listFilterMovie.add(FilterType(Constants.FilterType.TOP_RATED, false))
        adapterFilter = AdapterFilter(requireContext(), true, itemClickedListener = {
            when (it.type) {
                Constants.FilterType.AIRING_TODAY -> {
                    loadListTVSeries("airing_today")
                }
                Constants.FilterType.ON_AIR -> {
                    loadListTVSeries("on_the_air")
                }
                Constants.FilterType.POPULAR -> {
                    loadListTVSeries("popular")
                }
                else -> {
                    loadListTVSeries("top_rated")
                }
            }
        })
        binding.rvFilterTVSeries.adapter = adapterFilter
        adapterFilter.setData(listFilterMovie)
        adapterTVSeries = AdapterTVSeries(requireContext(), itemClickedListener = {
            navigateToFragment(
                HomeFragmentDirections.actionToDetailTVSeriesFragment(
                    it.id
                )
            )
        })
        binding.rvTVSeries.adapter = adapterTVSeries
    }


    private fun loadListTVSeries(type: String) {
        viewModel.loadListTVSeries(
            requireContext(),
            type,
            1,
            object : IMovieTrendingCallback {
                override fun onSuccess(response: TrendingResponse) {
                    adapterTVSeries.setData(response.results)
                }

                override fun onError(error: VolleyError) {
                    error.localizedMessage?.let { toastLong(it) }
                }
            })
    }


    private fun loadListMovies(type: String) {
        viewModel.loadListMovies(
            requireContext(),
            type,
            1,
            object : IMovieTrendingCallback {
                override fun onSuccess(response: TrendingResponse) {
                    if (type == "upcoming") {
                        mListMovieUpcoming.clear()
                        mListMovieUpcoming.addAll(response.results)
                        adapterUpcoming.setData(mListMovieUpcoming)
                        setupMovieUpcoming(0)
                    } else {
                        adapterMovie.setData(response.results)
                    }

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


}