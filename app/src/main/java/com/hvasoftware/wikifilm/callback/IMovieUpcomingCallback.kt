package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.data.response.UpcomingMovieResponse

interface IMovieUpcomingCallback : BaseCallback {

    fun onSuccess(response: UpcomingMovieResponse)

}