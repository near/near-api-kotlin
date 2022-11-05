package com.knear.android.provider.response.sendmoney

import com.google.gson.annotations.SerializedName


data class Status(

    @SerializedName("SuccessReceiptId") var SuccessReceiptId: String = ""

)
