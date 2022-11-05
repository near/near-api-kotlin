package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class ActionError(

    @SerializedName("index") var index: Int? = null,
    @SerializedName("kind") var kind: Kind = Kind()

)
