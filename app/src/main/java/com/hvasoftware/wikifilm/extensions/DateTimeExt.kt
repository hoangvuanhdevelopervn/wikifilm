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


// runtime = 148
fun convertMovieTime(runTime: Int): String {
    val hour = runTime / 60
    val minute = runTime - (60 * hour)
    return "${hour}h${minute}m"
}


// "birthday":"1994-05-04",
fun convertActorBirthday(birthday: String): String {
    var spf = SimpleDateFormat("yyyy-MM-dd")
    val newDate = spf.parse(birthday)
    spf = SimpleDateFormat("dd MMM, yyyy")
    return spf.format(newDate)
}




