package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.model.response.TrendingResponse

interface IMovieDetailCallback : BaseCallback {

    fun onSuccess(response: TrendingResponse)

}