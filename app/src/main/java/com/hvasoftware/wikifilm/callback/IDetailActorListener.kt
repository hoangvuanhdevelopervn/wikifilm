package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.model.Actor

interface IDetailActorListener {

    fun onSuccess(actor: Actor)

    fun onError(error: VolleyError)

}