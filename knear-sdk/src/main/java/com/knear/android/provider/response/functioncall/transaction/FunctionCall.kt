package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName
import java.math.BigInteger


data class FunctionCall(

    @SerializedName("args") var args: String? = null,
    @SerializedName("deposit") var deposit: BigInteger? = null,
    @SerializedName("gas") var gas: Long? = null,
    @SerializedName("method_name") var methodName: String? = null

)
