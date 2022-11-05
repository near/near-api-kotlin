package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Actions(

    @SerializedName("FunctionCall") var FunctionCall: FunctionCall? = FunctionCall()

)
