package com.hvasoftware.wikifilm.ui.actors

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.hvasoftware.wikifilm.callback.*
import com.hvasoftware.wikifilm.help.Constants
import com.hvasoftware.wikifilm.model.*
import com.hvasoftware.wikifilm.model.response.ListActorImageResponse
import com.hvasoftware.wikifilm.model.response.PopularActorResponse
import com.hvasoftware.wikifilm.model.response.SocialActorResponse
import com.hvasoftware.wikifilm.model.response.TrendingResponse
import org.json.JSONException
import org.json.JSONObject

class ActorsViewModel : ViewModel() {


    fun loadListActors(context: Context, page: Int, callback: IActorListener) {
        val queue = Volley.newRequestQueue(context)
        val urlPexel =
            "https://api.themoviedb.org/3/person/popular?api_key=18d9c9521c7d3ce97f566aae0838608e&language=en-US&page=$page"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlPexel, null, Response.Listener { response ->
                    callback.onSuccess(
                        handleActorResponse(response)
                    )
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    private fun handleActorResponse(response: JSONObject): PopularActorResponse {
        val actors: MutableList<Actor> = arrayListOf()
        val results = response.getJSONArray("results")
        val page = response.getInt("page")
        for (i in 0 until results.length()) {
            try {
                val responseObject = results.getJSONObject(i)
                val data =
                    Gson().fromJson(responseObject.toString(), Actor::class.java)
                actors.add(data)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return PopularActorResponse(page, actors)
    }


    fun loadActorDetail(context: Context, id: String, callback: IDetailActorListener) {
        val queue = Volley.newRequestQueue(context)
        val urlDetailActor =
            "https://api.themoviedb.org/3/person/$id?api_key=${Constants.API_KEY}&language=en-US"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlDetailActor, null, Response.Listener { response ->
                    val data =
                        Gson().fromJson(response.toString(), Actor::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadListActorImage(context: Context, id: String, callback: IListActorImageCallback) {
        val queue = Volley.newRequestQueue(context)
        val urlDetailActor =
            "https://api.themoviedb.org/3/person/$id/images?api_key=${Constants.API_KEY}"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlDetailActor, null, Response.Listener { response ->
                    val data =
                        Gson().fromJson(response.toString(), ListActorImageResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadListActorSocial(context: Context, id: String, callback: IActorSocialCallback) {
        val queue = Volley.newRequestQueue(context)
        val urlDetailActor =
            "https://api.themoviedb.org/3/person/$id/external_ids?api_key=${Constants.API_KEY}&language=en-US"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, urlDetailActor, null, Response.Listener { response ->
                    val data =
                        Gson().fromJson(response.toString(), SocialActorResponse::class.java)
                    callback.onSuccess(data)
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    fun loadListActorKnownFor(context: Context, id: String, callback: IMovieTrendingCallback) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "https://api.themoviedb.org/3/person/$id/combined_credits?api_key=${Constants.API_KEY}&language=en-US"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, url, null, Response.Listener { response ->
                    callback.onSuccess(handleResponse(response))
                }, Response.ErrorListener { error ->
                    callback.onError(error)
                    Log.wtf("getListImages", "error: ${Gson().toJson(error)}")
                }) {
            }
        queue.add(jsonObjectRequest)
    }

    private fun handleResponse(response: JSONObject): TrendingResponse {
        val movies: MutableList<Movie> = arrayListOf()
        val results = response.getJSONArray("cast")
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
        return TrendingResponse(1, movies)
    }

}