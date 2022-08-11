package com.knear.android.provider.response.genesisconfig

data class LimitConfig(
    val initial_memory_pages: Int,
    val max_actions_per_receipt: Int,
    val max_arguments_length: Int,
    val max_contract_size: Int,
    val max_gas_burnt: Long,
    val max_gas_burnt_view: Long,
    val max_length_method_name: Int,
    val max_length_returned_data: Int,
    val max_length_storage_key: Int,
    val max_length_storage_value: Int,
    val max_memory_pages: Int,
    val max_number_bytes_method_names: Int,
    val max_number_input_data_dependencies: Int,
    val max_number_logs: Int,
    val max_number_registers: Int,
    val max_promises_per_function_call_action: Int,
    val max_register_size: Int,
    val max_stack_height: Int,
    val max_total_log_length: Int,
    val max_total_prepaid_gas: Long,
    val registers_memory_limit: Int
)
