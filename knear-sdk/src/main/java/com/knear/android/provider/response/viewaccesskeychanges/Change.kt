package com.knear.android.provider.response.viewaccesskeychanges

data class Change(
    val cause: Cause,
    val change: ChangeX,
    val type: String
)
