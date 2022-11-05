package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Status(
    @SerializedName("SuccessReceiptId") var SuccessReceiptId: String? = null,
    @SerializedName("SuccessValue") var SuccessValue: String? = null,
    @SerializedName("Failure") var Failure: Failure? = null
)
