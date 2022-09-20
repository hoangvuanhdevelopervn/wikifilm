package com.hvasoftware.wikifilm.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.wikifilm.callback.IMovieVideoCallback
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.response.MovieVideoResponse

class MovieViewModel : ViewModel() {

    fun loadVideoMovie(
        context: Context,
        id: String,
        callback: IMovieVideoCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/movie/$id/videos?api_key=${Constants.API_KEY}&language=en-US";
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data = Gson().fromJson(response.toString(), MovieVideoResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadVideoTVSeries(
        context: Context,
        id: String,
        callback: IMovieVideoCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/tv/$id/videos?api_key=${Constants.API_KEY}&language=en-US"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data = Gson().fromJson(response.toString(), MovieVideoResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadDetailMovie(
        context: Context,
        id: String,
        callback: IMovieVideoCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/movie/$id?api_key=${Constants.API_KEY}&language=en-US\n"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data = Gson().fromJson(response.toString(), MovieVideoResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }




}