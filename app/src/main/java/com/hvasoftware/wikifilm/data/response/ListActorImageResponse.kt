package com.hvasoftware.wikifilm.data.response

import com.hvasoftware.wikifilm.data.ActorImage

data class ListActorImageResponse(val id: Int, val profiles: MutableList<ActorImage>) {
}