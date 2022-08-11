package com.knear.android.provider.response.protocolconfig

data class WasmConfig(
    val ext_costs: ExtCosts,
    val grow_mem_cost: Int,
    val limit_config: LimitConfig,
    val regular_op_cost: Int
)
