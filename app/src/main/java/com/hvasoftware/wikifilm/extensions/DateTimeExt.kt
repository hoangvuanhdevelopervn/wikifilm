package com.hvasoftware.wikifilm.extensions

import java.text.SimpleDateFormat
import java.util.*


/**
"release_date": "2022-07-06",
 */
fun convertDate(date: String?): String {
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val newDate = date?.let { simpleDateFormat.parse(it) }
    simpleDateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    return newDate?.let { simpleDateFormat.format(it) }.toString()
}

