package com.hvasoftware.wikifilm.model.response

import com.hvasoftware.wikifilm.model.Movie

data class UpcomingMovieResponse(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
}