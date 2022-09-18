package com.hvasoftware.wikifilm.model

data class Movie(
    val id: String,
    val title: String?,
    val original_title: String?,
    val original_name: String?,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String?,
    val first_air_date: String?,
    val vote_average: Double,
    val popularity: Double
) {
}