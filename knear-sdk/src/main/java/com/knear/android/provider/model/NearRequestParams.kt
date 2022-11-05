package com.knear.android.provider.model

import com.google.gson.annotations.SerializedName

class NearRequestParams(
    @SerializedName("finality")
    val finality: String? = null,
    @SerializedName("block_id")
    val blockId: String? = null,
    @SerializedName("request_type")
    val requestType: String? = null,
    @SerializedName("account_id")
    val accountId: String? = null,
    @SerializedName("public_key")
    val publicKey: String? = null,
    @SerializedName("method_name")
    val methodName: String? = null,
    @SerializedName("args_base64")
    val argsBase64: String? = null,
)
