package com.knear.android.provider.response.protocolconfig

data class CostPerByte(
    val execution: Int,
    val send_not_sir: Int,
    val send_sir: Int
)
