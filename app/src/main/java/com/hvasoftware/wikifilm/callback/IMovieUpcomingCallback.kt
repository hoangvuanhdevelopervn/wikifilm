package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.model.response.UpcomingMovieResponse

interface IMovieUpcomingCallback : BaseCallback {

    fun onSuccess(response: UpcomingMovieResponse)

}