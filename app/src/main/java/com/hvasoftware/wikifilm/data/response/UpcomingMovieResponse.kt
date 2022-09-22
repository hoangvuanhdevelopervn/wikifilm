package com.hvasoftware.wikifilm.data.response

import com.hvasoftware.wikifilm.data.Movie

data class UpcomingMovieResponse(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
}