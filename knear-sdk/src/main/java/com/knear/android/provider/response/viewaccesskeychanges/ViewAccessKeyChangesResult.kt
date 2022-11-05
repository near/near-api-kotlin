package com.knear.android.provider.response.viewaccesskeychanges

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.knear.android.provider.response.errorresponse.ErrorResponse
import okhttp3.Response

open class ViewAccessKeyChangesResult(
    @SerializedName("jsonrpc") var jsonrpc: String? = null,
    @SerializedName("result") var result: Result = Result(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("error") var error: ErrorResponse? = null
) {

    companion object {
        @JvmStatic
        @JvmName("create")
        fun Response.toViewAccessKeyChanges(): ViewAccessKeyChangesResult {
            val gson = Gson()
            if (this.isSuccessful) {
                this.body?.let {
                    return gson.fromJson(it.string(), ViewAccessKeyChangesResult::class.java)
                } ?: ViewAccessKeyChangesResult()
            }
            return ViewAccessKeyChangesResult()
        }
    }

}
