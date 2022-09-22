package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError
import com.hvasoftware.wikifilm.data.response.ListActorImageResponse

interface IListActorImageCallback {

    fun onSuccess(response: ListActorImageResponse)

    fun onError(error: VolleyError)

}