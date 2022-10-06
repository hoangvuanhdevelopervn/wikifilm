package com.hvasoftware.wikifilm.data

data class Movie(
    val id: String,
    val title: String?,
    val original_title: String?,
    val original_name: String?,
    val backdrop_path: String,
    val homepage: String,
    val status: String,
    val overview: String,
    val genres: MutableList<Genre>,
    val poster_path: String,
    val release_date: String?,
    val media_type: String?,
    val first_air_date: String?,
    val vote_average: Double,
    val vote_count: Int,
    val runtime: Int,
    val popularity: Double
) {
}