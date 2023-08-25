package com.hvasoftware.wikifilm.data.response


data class WikipediaResponse(
    val query: Query
)

data class Query(
    val pages: Map<String, Page>
)

data class Page(
    val title: String,
    val extract: String
)
