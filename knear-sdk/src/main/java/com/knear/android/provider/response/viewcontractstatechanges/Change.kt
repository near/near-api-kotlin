package com.knear.android.provider.response.viewcontractstatechanges

data class Change(
    val cause: Cause,
    val change: ChangeX,
    val type: String
)
