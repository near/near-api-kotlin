package com.knear.android.provider.response.viewaccesskey

import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("block_hash") var blockHash: String? = null,
    @SerializedName("block_height") var blockHeight: Long = 0L,
    @SerializedName("nonce") var nonce: Long = 0L,
    @SerializedName("permission") var permission: String? = null
)
