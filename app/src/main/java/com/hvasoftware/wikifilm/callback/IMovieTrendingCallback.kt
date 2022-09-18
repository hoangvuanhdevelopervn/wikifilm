package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.model.TrendingResponse

interface IMovieTrendingCallback {

    fun onSuccess(response: TrendingResponse)

    fun onError(error: VolleyError)
}