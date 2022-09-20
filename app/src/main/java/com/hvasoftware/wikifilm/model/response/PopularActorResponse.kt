package com.hvasoftware.wikifilm.model.response

import com.hvasoftware.wikifilm.model.Actor

data class PopularActorResponse(val page: Int, val results: MutableList<Actor>)
