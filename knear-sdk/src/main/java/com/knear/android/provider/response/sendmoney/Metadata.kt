package com.knear.android.provider.response.sendmoney

import com.google.gson.annotations.SerializedName


data class Metadata(

    @SerializedName("gas_profile") var gasProfile: List<String> = listOf(""),
    @SerializedName("version") var version: Int = 0

)
