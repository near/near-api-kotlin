package com.knear.android.provider.response.sendmoney

import com.google.gson.annotations.SerializedName


data class Transfer(

    @SerializedName("deposit") var deposit: String? = null

)
