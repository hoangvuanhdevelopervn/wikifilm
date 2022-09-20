package com.hvasoftware.wikifilm.callback

import com.android.volley.VolleyError

interface BaseCallback {

    fun onError(error: VolleyError)

}