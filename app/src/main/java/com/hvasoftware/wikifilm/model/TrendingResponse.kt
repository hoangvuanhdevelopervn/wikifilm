package com.hvasoftware.wikifilm.model


data class TrendingResponse(val page: Int, val results: MutableList<Movie>)
