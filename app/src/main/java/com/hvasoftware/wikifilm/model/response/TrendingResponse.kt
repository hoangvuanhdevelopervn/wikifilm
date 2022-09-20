package com.hvasoftware.wikifilm.model.response

import com.hvasoftware.wikifilm.model.Movie


data class TrendingResponse(val page: Int, val results: MutableList<Movie>)
