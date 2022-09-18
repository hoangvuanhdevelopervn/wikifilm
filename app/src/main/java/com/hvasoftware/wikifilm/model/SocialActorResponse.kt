package com.hvasoftware.wikifilm.model

data class SocialActorResponse(
    val id: Int,
    val freebase_mid: String,
    val freebase_id: String,
    val imdb_id: String,
    val tvrage_id: Int,
    val facebook_id: String,
    val instagram_id: String,
    val twitter_id: String,
)
