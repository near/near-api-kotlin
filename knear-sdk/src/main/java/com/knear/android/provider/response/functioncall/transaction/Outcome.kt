package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Outcome(

    @SerializedName("executor_id") var executorId: String? = null,
    @SerializedName("gas_burnt") var gasBurnt: Long? = null,
    @SerializedName("logs") var logs: ArrayList<String> = arrayListOf(),
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("receipt_ids") var receiptIds: ArrayList<String> = arrayListOf(),
    @SerializedName("status") var status: Status? = Status(),
    @SerializedName("tokens_burnt") var tokensBurnt: String? = null

)
