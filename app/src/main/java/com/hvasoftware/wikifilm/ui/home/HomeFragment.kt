package com.hvasoftware.wikifilm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.callback.IMovieTrendingCallback
import com.hvasoftware.wikifilm.callback.IScrollListener
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.databinding.FragmentHomeBinding
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.extensions.toastLong
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.TrendingResponse

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterTrending: AdapterTrending


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        loadData(Constants.MediaType.ALL)

        onViewClick()

    }

    private fun setupAdapter() {
        adapterTrending = AdapterTrending(requireContext());
        binding.rvTrending.adapter = adapterTrending
    }


    private fun loadData(type: Constants.MediaType) {
        viewModel.loadListMovieTrending(
            requireContext(),
            type.toString().lowercase(),
            Constants.TimeWindow.WEEK.toString().lowercase(),
            object : IMovieTrendingCallback {
                override fun onSuccess(response: TrendingResponse) {
                    // binding.pbLoading.hide()
                    adapterTrending.setData(response.results)
                }

                override fun onError(error: VolleyError) {
                    //  binding.pbLoading.hide()
                    error.localizedMessage?.let { toastLong(it) }
                }
            })
    }

    // Upcoming
    // https://api.themoviedb.org/3/movie/upcoming?api_key=18d9c9521c7d3ce97f566aae0838608e&language=pt-BR&page=1&region=BR
    private fun onViewClick() {
        binding.typeAll.setOnClickListener {
            loadData(Constants.MediaType.ALL)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeTVSeries.setOnClickListener {
            loadData(Constants.MediaType.TV)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeMovie.setOnClickListener {
            loadData(Constants.MediaType.MOVIE)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type_selected)
        }
    }
}