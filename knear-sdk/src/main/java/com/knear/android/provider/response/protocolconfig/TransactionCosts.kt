package com.knear.android.provider.response.protocolconfig

data class TransactionCosts(
    val action_creation_config: ActionCreationConfig,
    val action_receipt_creation_config: ActionReceiptCreationConfig,
    val burnt_gas_reward: List<Int>,
    val data_receipt_creation_config: DataReceiptCreationConfig,
    val pessimistic_gas_price_inflation_ratio: List<Int>,
    val storage_usage_config: StorageUsageConfig
)
