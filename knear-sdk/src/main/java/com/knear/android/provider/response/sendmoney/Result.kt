package com.knear.android.provider.response.sendmoney

import com.google.gson.annotations.SerializedName


data class Result(

    @SerializedName("receipts_outcome") var receiptsOutcome: ArrayList<ReceiptsOutcome> = arrayListOf(),
    @SerializedName("status") var status: Status = Status(),
    @SerializedName("transaction") var transaction: Transaction = Transaction(),
    //@SerializedName("transaction_outcome") var transactionOutcome : TransactionOutcome? = TransactionOutcome()

)
