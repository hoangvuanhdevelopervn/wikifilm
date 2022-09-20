package com.hvasoftware.wikifilm.model.response

data class DetailMovieResponse(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val homepage: String,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: String,
    val runtime: String,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)
