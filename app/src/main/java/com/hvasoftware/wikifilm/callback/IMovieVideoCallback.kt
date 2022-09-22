package com.hvasoftware.wikifilm.callback

import com.hvasoftware.wikifilm.data.response.MovieVideoResponse

interface IMovieVideoCallback :BaseCallback {

    fun onSuccess(response: MovieVideoResponse)
}