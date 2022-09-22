package com.hvasoftware.wikifilm.data.response

import com.hvasoftware.wikifilm.data.Actor

data class PopularActorResponse(val page: Int, val results: MutableList<Actor>)
