package com.knear.android.provider

interface NearResponseListener {
    fun onError(error: Exception)
    fun onResponse(response: NearResponse)
}
