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
import com.hvasoftware.wikifilm.model.response.TrendingResponse
import org.json.JSONException
import org.json.JSONObject

class HomeViewModel : ViewModel() {


    // https://api.themoviedb.org/3/movie/latest?api_key=18d9c9521c7d3ce97f566aae0838608e&language=en-US
    // https://api.themoviedb.org/3/movie/now_playing?api_key=18d9c9521c7d3ce97f566aae0838608e&language=en-US&page=1

    fun loadListMovies(
        context: Context,
        type: String,
        page: Int,
        callback: IMovieTrendingCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/movie/$type?api_key=${Constants.API_KEY}&language=en-US&page=$page"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data =
                        Gson().fromJson(response.toString(), TrendingResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadListTVSeries(
        context: Context,
        type: String,
        page: Int,
        callback: IMovieTrendingCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/tv/$type?api_key=${Constants.API_KEY}&language=en-US&page=$page"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data =
                        Gson().fromJson(response.toString(), TrendingResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                }) {
            }
        queue.add(jsonObjectRequest)
    }


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