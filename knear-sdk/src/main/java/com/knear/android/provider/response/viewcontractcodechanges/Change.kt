package com.knear.android.provider.response.viewcontractcodechanges

data class Change(
    val cause: Cause,
    val change: ChangeX,
    val type: String
)
