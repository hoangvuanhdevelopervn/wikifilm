package com.hvasoftware.wikifilm.data.response

import com.hvasoftware.wikifilm.data.MovieVideo

data class MovieVideoResponse(val id: Int, val results: MutableList<MovieVideo>?) {
}