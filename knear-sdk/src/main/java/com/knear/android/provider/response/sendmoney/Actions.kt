package com.knear.android.provider.response.sendmoney

import com.google.gson.annotations.SerializedName


data class Actions(

    @SerializedName("Transfer") var Transfer: Transfer? = Transfer()

)
