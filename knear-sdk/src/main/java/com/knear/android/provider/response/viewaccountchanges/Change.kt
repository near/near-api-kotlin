package com.knear.android.provider.response.viewaccountchanges

data class Change(
    val cause: Cause,
    val change: ChangeX,
    val type: String
)
