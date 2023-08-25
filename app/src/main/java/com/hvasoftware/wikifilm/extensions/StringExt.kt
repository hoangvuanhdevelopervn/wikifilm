package com.hvasoftware.wikifilm.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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


internal fun shareText(context: Context, textToShare: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}


internal fun copyToClipboard(context: Context, textToCopy: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("Copied Text", textToCopy)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
}


