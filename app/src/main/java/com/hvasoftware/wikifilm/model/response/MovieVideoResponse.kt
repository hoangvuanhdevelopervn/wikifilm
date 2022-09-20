package com.hvasoftware.wikifilm.model.response

import com.hvasoftware.wikifilm.model.MovieVideo

data class MovieVideoResponse(val id: Int, val results: MutableList<MovieVideo>?) {
}