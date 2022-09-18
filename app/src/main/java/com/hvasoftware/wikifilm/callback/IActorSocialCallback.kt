package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.model.SocialActorResponse

interface IActorSocialCallback {

    fun onSuccess(response: SocialActorResponse)

    fun onError(error: VolleyError)
}