package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.model.PopularActorResponse

interface IActorListener {

    fun onSuccess(response: PopularActorResponse)

    fun onError(error: VolleyError)

}