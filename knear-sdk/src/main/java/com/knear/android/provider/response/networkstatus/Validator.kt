package com.knear.android.provider.response.networkstatus

data class Validator(
    val account_id: String,
    val is_slashed: Boolean
)
