package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class TransactionOutcome(

    @SerializedName("block_hash") var blockHash: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("outcome") var outcome: Outcome = Outcome(),
    @SerializedName("proof") var proof: ArrayList<Proof> = arrayListOf()

)
