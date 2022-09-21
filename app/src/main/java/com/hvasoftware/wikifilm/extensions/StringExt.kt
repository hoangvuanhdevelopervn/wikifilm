package com.hvasoftware.wikifilm.extensions

import com.hvasoftware.wikifilm.help.Constants


internal fun getFilterName(type: Constants.FilterType): String {
    var name = ""
    when (type) {
        Constants.FilterType.ALL -> {
            name = "All"
        }
        Constants.FilterType.ON_AIR -> {
            name = "On TV"
        }
        Constants.FilterType.LATEST -> {
            name = "Latest"
        }
        Constants.FilterType.AIRING_TODAY -> {
            name = "Airing Today"
        }
        Constants.FilterType.MOVIE -> {
            name = "Movie"
        }
        Constants.FilterType.NOW_PLAYING -> {
            name = "Now Playing"
        }
        Constants.FilterType.TOP_RATED -> {
            name = "Top Rated"
        }
        Constants.FilterType.TV_SERIES -> {
            name = "TV Series"
        }
        Constants.FilterType.UPCOMING -> {
            name = "Upcoming"
        }
        Constants.FilterType.POPULAR -> {
            name = "Popular"
        }
        else -> {
            name = "All"
        }
    }
    return name
}