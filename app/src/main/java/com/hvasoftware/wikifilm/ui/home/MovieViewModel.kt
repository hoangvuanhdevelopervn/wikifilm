package com.hvasoftware.wikifilm.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.wikifilm.base.BaseResponse
import com.hvasoftware.wikifilm.callback.IMovieVideoCallback
import com.hvasoftware.wikifilm.data.Movie
import com.hvasoftware.wikifilm.data.MovieRepository
import com.hvasoftware.wikifilm.data.response.MovieVideoResponse
import com.hvasoftware.wikifilm.help.Constants

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {


    val loadDetailMovieState: MutableLiveData<BaseResponse<Movie>> =
        MutableLiveData(BaseResponse.Idle)


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
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/movie/$id?api_key=${Constants.API_KEY}&language=en-US\n"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    val data = Gson().fromJson(response.toString(), Movie::class.java)
                    loadDetailMovieState.value = BaseResponse.Success(data)
                }, Response.ErrorListener { error ->
                    loadDetailMovieState.value = BaseResponse.Error(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }



}


class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}