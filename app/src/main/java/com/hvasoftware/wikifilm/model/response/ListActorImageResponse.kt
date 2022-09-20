package com.hvasoftware.wikifilm.model.response

import com.hvasoftware.wikifilm.model.ActorImage

data class ListActorImageResponse(val id: Int, val profiles: MutableList<ActorImage>) {
}