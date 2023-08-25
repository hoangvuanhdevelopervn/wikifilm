package com.hvasoftware.wikifilm.ui.actors

import android.text.Html
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hvasoftware.wikifilm.R
import com.hvasoftware.wikifilm.base.BaseFragment
import com.hvasoftware.wikifilm.databinding.FragmentBioActorBinding
import com.hvasoftware.wikifilm.extensions.*
import com.hvasoftware.wikifilm.extensions.copyToClipboard
import com.hvasoftware.wikifilm.extensions.logger
import com.hvasoftware.wikifilm.extensions.shareText
import org.json.JSONObject

class BiographyActorFragment : BaseFragment() {

    private lateinit var binding: FragmentBioActorBinding
    private val args: BiographyActorFragmentArgs by navArgs()
    private var mActorName = ""
    private var mActorBiography = ""


    override fun setContentView(): View {
        binding = FragmentBioActorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewClick() {

        binding.headerBar.ivBack.setOnClickListener { onBackPress() }
        binding.headerBar.ivMenu.setOnClickListener {
            showPopupMenu(binding.headerBar.ivMenu)
        }

    }

    override fun initData() {
        super.initData()

        handleArgument()

    }

    override fun loadData() {

    }

    private fun handleArgument() {
        binding.headerBar.tvTitle.text = "Biography"
        binding.headerBar.ivMenu.show()
        mActorBiography = args.actorBio.toString()
        binding.tvActorBio.text = args.actorBio.toString()
        mActorName = args.actorName.toString()
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_actor_biography, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_wiki -> {
                    binding.tvActorBio.text = ""
                    fetchBiographyFromWikipedia()
                    true
                }
                R.id.action_share -> {
                    shareText(requireContext(), mActorBiography)
                    true
                }
                R.id.action_copy -> {
                    copyToClipboard(requireContext(), mActorBiography)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }


    private fun fetchBiographyFromWikipedia() {
        binding.pbLoading.show()
        val requestQueue = Volley.newRequestQueue(context)
        mActorName = mActorName.replace(" ", "%20")
        val url =
            "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&titles=$mActorName"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    binding.pbLoading.hide()
                    val extractString = extractBiographyFromResponse(response)
                    if (extractString != null) {
                        mActorBiography = extractString
                        binding.tvActorBio.text =
                            Html.fromHtml(extractString, Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        toastLong("Can not load data")
                    }
                }, Response.ErrorListener { error ->
                    logger("error: $error")
                    binding.pbLoading.hide()
                    toastLong(error.toString())
                }) {
            }
        requestQueue.add(jsonObjectRequest)
    }

    fun extractBiographyFromResponse(response: JSONObject): String? {
        try {
            val queryObject = response.getJSONObject("query")
            val pagesObject = queryObject.getJSONObject("pages")
            val pageKey = pagesObject.keys().next()
            val pageObject = pagesObject.getJSONObject(pageKey)
            return pageObject.optString("extract")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}