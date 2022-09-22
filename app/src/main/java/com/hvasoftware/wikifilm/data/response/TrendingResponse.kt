package com.hvasoftware.wikifilm.data.response

import com.hvasoftware.wikifilm.data.Movie


data class TrendingResponse(val page: Int, val results: MutableList<Movie>)
