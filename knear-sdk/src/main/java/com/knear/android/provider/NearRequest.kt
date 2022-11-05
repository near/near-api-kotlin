package com.knear.android.provider

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.knear.android.provider.model.NearRequestParams

class NearRequest(
    @SerializedName("method")
    private val method: String,
    @SerializedName("params")
    private val params: NearRequestParams,
    @SerializedName("id")
    private val currentId: Int
) {
    @SerializedName("jsonrpc")
    private var jsonRpc: String = "2.0"

    override fun toString(): String {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        return gson.toJson(this)
    }
}
