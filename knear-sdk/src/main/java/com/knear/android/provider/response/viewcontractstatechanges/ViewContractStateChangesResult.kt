package com.knear.android.provider.response.viewcontractstatechanges

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.knear.android.provider.response.errorresponse.ErrorResponse
import okhttp3.Response

data class ViewContractStateChangesResult(
    @SerializedName("jsonrpc") var jsonrpc: String? = null,
    @SerializedName("result") var result: Result = Result(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("error") var error: ErrorResponse? = null
) {

    companion object {
        @JvmStatic
        @JvmName("create")
        fun Response.toViewContractStateChanges(): ViewContractStateChangesResult {
            val gson = Gson()
            if (this.isSuccessful) {
                this.body?.let {
                    return gson.fromJson(it.string(), ViewContractStateChangesResult::class.java)
                } ?: ViewContractStateChangesResult()
            }
            return ViewContractStateChangesResult()
        }
    }

}
