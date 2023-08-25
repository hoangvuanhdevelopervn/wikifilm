package com.hvasoftware.wikifilm.ui.actors

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.callback.IActorSocialCallback
import com.hvasoftware.wikifilm.callback.IDetailActorListener
import com.hvasoftware.wikifilm.callback.IListActorImageCallback
import com.hvasoftware.wikifilm.callback.IMovieTrendingCallback
import com.hvasoftware.wikifilm.data.Actor
import com.hvasoftware.wikifilm.data.response.ListActorImageResponse
import com.hvasoftware.wikifilm.data.response.SocialActorResponse
import com.hvasoftware.wikifilm.data.response.TrendingResponse
import com.hvasoftware.wikifilm.databinding.FragmentDetailActorBinding
import com.hvasoftware.wikifilm.extensions.*
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.ui.home.adapter.AdapterTrending
import kotlin.random.Random

class DetailActorFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailActorBinding
    private val args: DetailActorFragmentArgs by navArgs()
    private var mId = ""
    private lateinit var adapterActorImage: AdapterActorImage
    private lateinit var adapterTrending: AdapterTrending
    private var mTotalImage = 0
    private var mActorName = ""
    private var mActorBiography = ""


    override fun setContentView(): View {
        binding = FragmentDetailActorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewClick() {
        binding.headerBar.ivBack.setOnClickListener { onBackPress() }

        binding.tvActorBio.setOnClickListener {
            navigateToFragment(
                DetailActorFragmentDirections.actionToBiographyActorFragment(
                    mActorName,
                    mActorBiography
                )
            )
        }

        onClickType()
    }

    override fun initData() {
        super.initData()

        handleArgument()

        setupAdapterImage()

        setupAdapterTrending()

    }

    private fun handleArgument() {
        mId = args.actorId.toString()
        mActorName = args.actorName.toString()
        binding.headerBar.tvTitle.text = args.actorName
    }


    private fun setupAdapterImage() {
        adapterActorImage = AdapterActorImage(requireContext(), itemClickedListener = {

        })
        binding.rvActorImage.adapter = adapterActorImage
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvActorImage)
        binding.rvActorImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val mCurrentPosition = binding.rvActorImage.getCurrentPosition()
                    binding.tvNumber.text = "${mCurrentPosition + 1}/$mTotalImage"
                }
            }
        })
    }

    private fun setupAdapterTrending() {
        adapterTrending = AdapterTrending(requireContext(), itemClickedListener = {

        })
        binding.rvActorKnownFor.adapter = adapterTrending
    }


    override fun loadData() {

        loadListActorImages()

        loadActorInfo()

        loadActorSocial()

        loadActorKnownFor()

    }


    private fun loadListActorImages() {
        actorsViewModel.loadListActorImage(requireContext(), mId, object : IListActorImageCallback {
            override fun onSuccess(response: ListActorImageResponse) {
                binding.pbLoading.hide()
                val data = response.profiles
                adapterActorImage.setData(data)
                mTotalImage = data.size
                binding.tvNumber.text = "1/${data.size}"
                if (data.isNullOrEmpty()) {
                    toastLong("No Data")
                    onBackPress()
                } else if (response.profiles.size > 2) {
                    val rdPosition = Random.nextInt(data.size - 1)
                    binding.sivActor.setUrl("${Constants.BASE_URL_IMAGE}${data[rdPosition].file_path}")
                    binding.civActor.setUrl("${Constants.BASE_URL_IMAGE}${data[rdPosition].file_path}")
                } else {
                    binding.sivActor.setUrl("${Constants.BASE_URL_IMAGE}${data[0].file_path}")
                    binding.civActor.setUrl("${Constants.BASE_URL_IMAGE}${data[0].file_path}")
                }
            }

            override fun onError(error: VolleyError) {
                binding.pbLoading.hide()
                error.localizedMessage?.let { toastLong(it) }
            }
        })
    }

    private fun loadActorInfo() {
        actorsViewModel.loadActorDetail(
            requireContext(),
            mId,
            object : IDetailActorListener {
                override fun onSuccess(actor: Actor) {
                    mActorBiography = actor.biography
                    binding.tvActorBio.text = actor.biography
                    binding.tvActorBorn.text = actor.place_of_birth
                }

                override fun onError(error: VolleyError) {
                    logger("Error: $error")
                }
            })
    }


    private fun loadActorSocial() {
        actorsViewModel.loadListActorSocial(
            requireContext(),
            mId,
            object : IActorSocialCallback {
                override fun onSuccess(response: SocialActorResponse) {

                }

                override fun onError(error: VolleyError) {
                    logger("Error: $error")
                }
            })
    }


    private fun loadActorKnownFor() {
        actorsViewModel.loadListActorKnownFor(
            requireContext(),
            mId,
            object : IMovieTrendingCallback {
                override fun onSuccess(response: TrendingResponse) {
                    adapterTrending.setData(response.results)
                }

                override fun onError(error: VolleyError) {
                    error.localizedMessage?.let { toastLong(it) }
                }
            })
    }


    private fun onClickType() {
        binding.typeAll.setOnClickListener {
            //  loadData(Constants.MediaType.ALL)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeTVSeries.setOnClickListener {
            //  loadData(Constants.MediaType.TV)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type_selected)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type)
        }
        binding.typeMovie.setOnClickListener {
            //  loadData(Constants.MediaType.MOVIE)
            binding.typeAll.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeTVSeries.setBackgroundResource(R.drawable.bg_view_small_type)
            binding.typeMovie.setBackgroundResource(R.drawable.bg_view_small_type_selected)
        }
    }


}