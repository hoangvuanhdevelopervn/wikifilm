package com.hvasoftware.wikifilm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.ui.actors.ActorsViewModel
import com.hvasoftware.wikifilm.ui.home.MovieViewModel

abstract class BaseFragment : Fragment(), BaseContract.View, BaseContract.Data {

    protected val TAG = this.javaClass.simpleName
    protected val actorsViewModel: ActorsViewModel by viewModels()
    protected val movieViewModel: MovieViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setContentView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        onViewClick()
    }

    override fun initData() {

    }

    protected fun navigateToFragment(fragment: Int, args: Bundle? = null) {
        try {
            findNavController().navigate(fragment, args)
        } catch (ignored: Exception) {
            logger(ignored.localizedMessage)
        }
    }

    protected fun navigateToFragment(navDirection: NavDirections) {
        try {
            findNavController().navigate(navDirection)
        } catch (ignored: Exception) {
            logger(ignored.localizedMessage)
        }
    }

    fun onBackPress(){
        requireActivity().onBackPressed()
    }


}