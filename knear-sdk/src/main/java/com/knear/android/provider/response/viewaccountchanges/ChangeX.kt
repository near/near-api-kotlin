package com.knear.android.provider.response.viewaccountchanges

data class ChangeX(
    val account_id: String,
    val amount: String,
    val code_hash: String,
    val locked: String,
    val storage_paid_at: Int,
    val storage_usage: Int
)
