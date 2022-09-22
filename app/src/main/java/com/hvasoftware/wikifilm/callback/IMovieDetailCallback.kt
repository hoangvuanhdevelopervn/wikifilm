package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.data.response.TrendingResponse

interface IMovieDetailCallback : BaseCallback {

    fun onSuccess(response: TrendingResponse)

}