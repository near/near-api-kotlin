package com.knear.android.provider.response.networkstatus

data class Result(
    val chain_id: String = "",
    val latest_protocol_version: Int = 0,
    val protocol_version: Int = 0,
    val rpc_addr: String = "",
    val sync_info: SyncInfo? = null,
    val validator_account_id: String = "",
    val validators: List<Validator> = listOf(),
    val version: Version? = null
)
