package com.knear.android.provider.response.functioncall

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("block_hash") var blockHash: String? = null,
    @SerializedName("block_height") var blockHeight: Long? = null,
    @SerializedName("logs") var logs: ArrayList<String> = arrayListOf(),
    @SerializedName("result") var result: ByteArray? = null
)
