package com.hvasoftware.wikifilm.data

data class Actor(
    val adult: Boolean,
    val gender: Int,
    val id: String,
    val known_for: MutableList<KnowFor>,
    val known_for_department: String,
    val popularity: Double,
    val profile_path: String,
    val place_of_birth: String,
    val homepage: String,
    val also_known_as: MutableList<String>,
    val biography: String,
    val deathday: String,
    val imdb_id: String,
    val birthday: String,
    val name: String
)
