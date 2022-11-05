package com.knear.android.provider.model

class ViewContractStateChangesParams(
    val changes_type: String,
    val account_ids: List<String>,
    val key_prefix_base64: String,
    val block_id: Int
)
