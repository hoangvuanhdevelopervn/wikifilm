package com.hvasoftware.wikifilm.help

import com.google.gson.Gson

class WikipediaAPI {
//    private val client = OkHttpClient()
//    private val gson = Gson()
//
//    fun getBiography(name: String): String {
//        val url = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=$name&exintro=true"
//
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        val response = client.newCall(request).execute()
//        val body = response.body?.string()
//
//        val wikipediaResponse = gson.fromJson(body, WikipediaResponse::class.java)
//
//        // Extract and return the biography
//        return wikipediaResponse.query.pages.values.firstOrNull()?.extract ?: "Biography not found"
//    }
}
