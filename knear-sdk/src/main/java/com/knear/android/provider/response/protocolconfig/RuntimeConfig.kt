package com.knear.android.provider.response.protocolconfig

data class RuntimeConfig(
    val account_creation_config: AccountCreationConfig,
    val storage_amount_per_byte: String,
    val transaction_costs: TransactionCosts,
    val wasm_config: WasmConfig
)
