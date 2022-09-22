package com.hvasoftware.wikifilm.base

import com.android.volley.VolleyError

sealed class BaseResponse<out T : Any> {
    object Idle : BaseResponse<Nothing>()
    object Loading : BaseResponse<Nothing>()
    class Success<out T : Any>(val data: T) : BaseResponse<T>()
    class Error(val serverError: VolleyError? = null) : BaseResponse<Nothing>()
}
