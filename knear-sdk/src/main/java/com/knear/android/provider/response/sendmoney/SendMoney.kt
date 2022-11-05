package com.knear.android.provider.response.sendmoney

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.knear.android.provider.response.errorresponse.ErrorResponse
import okhttp3.Response


data class SendMoney(

    @SerializedName("jsonrpc") var jsonrpc: String? = null,
    @SerializedName("result") var result: Result = Result(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("error") var error: ErrorResponse? = null
) {
    companion object {
        @JvmStatic
        @JvmName("create")
        fun Response.toSendMoney(): SendMoney {
            if (this.isSuccessful) {
                val gson = Gson()
                val jsonString = this.body!!.string()
                return gson.fromJson(jsonString, SendMoney::class.java)
            }
            return SendMoney()
        }
    }
}
