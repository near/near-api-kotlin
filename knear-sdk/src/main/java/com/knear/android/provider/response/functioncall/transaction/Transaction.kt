package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Transaction(

    @SerializedName("actions") var actions: ArrayList<Actions> = arrayListOf(),
    @SerializedName("hash") var hash: String? = null,
    @SerializedName("nonce") var nonce: Long? = null,
    @SerializedName("public_key") var publicKey: String? = null,
    @SerializedName("receiver_id") var receiverId: String? = null,
    @SerializedName("signature") var signature: String? = null,
    @SerializedName("signer_id") var signerId: String? = null

)
