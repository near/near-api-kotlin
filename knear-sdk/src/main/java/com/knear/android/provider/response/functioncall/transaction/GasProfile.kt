package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class GasProfile(

    @SerializedName("cost") var cost: String? = null,
    @SerializedName("cost_category") var costCategory: String? = null,
    @SerializedName("gas_used") var gasUsed: String? = null

)
