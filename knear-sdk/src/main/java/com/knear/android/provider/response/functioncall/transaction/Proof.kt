package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Proof(

    @SerializedName("direction") var direction: String? = null,
    @SerializedName("hash") var hash: String? = null

)
