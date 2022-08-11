package com.knear.android.provider.response.viewaccount

data class Result(
    val amount: String = "",
    val block_hash: String = "",
    val block_height: Int = 0,
    val code_hash: String = "",
    val locked: String = "",
    val storage_paid_at: Int = 0,
    val storage_usage: Int = 0
)
