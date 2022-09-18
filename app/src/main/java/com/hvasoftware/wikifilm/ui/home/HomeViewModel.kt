package com.hvasoftware.wikifilm.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.wikifilm.callback.IMovieTrendingCallback
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.Movie
import com.hvasoftware.wikifilm.model.TrendingResponse
import org.json.JSONException
import org.json.JSONObject

class HomeViewModel : ViewModel() {

    fun loadListMovieTrending(
        context: Context,
        type: String,
        time: String,
        callback: IMovieTrendingCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val urlTrending =
            "https://api.themoviedb.org/3/trending/$type/$time?api_key=${Constants.API_KEY}"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlTrending, null, Response.Listener { response ->
                    callback.onSuccess(
                        handleResponse(response)
                    )
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    private fun handleResponse(response: JSONObject): TrendingResponse {
        val movies: MutableList<Movie> = arrayListOf()
        val results = response.getJSONArray("results")
        val page = response.getInt("page")
        for (i in 0 until results.length()) {
            try {
                val responseObject = results.getJSONObject(i)
                val data =
                    Gson().fromJson(responseObject.toString(), Movie::class.java)
                movies.add(data)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return TrendingResponse(page, movies)
    }
}