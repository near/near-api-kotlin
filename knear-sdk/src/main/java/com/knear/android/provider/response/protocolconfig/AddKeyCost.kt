package com.knear.android.provider.response.protocolconfig

data class AddKeyCost(
    val full_access_cost: FullAccessCost,
    val function_call_cost: FunctionCallCostX,
    val function_call_cost_per_byte: FunctionCallCostPerByteX
)
