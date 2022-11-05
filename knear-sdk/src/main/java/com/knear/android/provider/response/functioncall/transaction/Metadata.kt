package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Metadata(

    @SerializedName("gas_profile") var gasProfile: Any = {},
    @SerializedName("version") var version: Int? = null

)
