package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.model.response.MovieVideoResponse

interface IMovieVideoCallback :BaseCallback {

    fun onSuccess(response: MovieVideoResponse)
}