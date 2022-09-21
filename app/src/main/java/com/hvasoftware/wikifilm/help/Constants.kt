package com.hvasoftware.wikifilm.help

object Constants {


    const val APP_NAME = "TuDienDanhNgon"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "18d9c9521c7d3ce97f566aae0838608e"
    const val GOOGLE_API_KEY = "AIzaSyCJ2WQgICNM1RTBJDnU6EHR2ZSB3XIcOR4"

    enum class FilterType {
        ALL,
        NOW_PLAYING,
        MOVIE,
        UPCOMING,
        ON_AIR,
        TV_SERIES,
        LATEST,
        AIRING_TODAY,
        ON_TV,
        POPULAR,
        TOP_RATED
    }

    enum class MediaType {
        ALL,
        TV,
        MOVIE,
        PERSON
    }

    enum class TimeWindow {
        DAY,
        WEEK
    }

}