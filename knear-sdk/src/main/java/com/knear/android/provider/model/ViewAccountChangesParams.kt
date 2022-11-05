package com.knear.android.provider.model

class ViewAccountChangesParams(
    val changes_type: String,
    val account_ids: List<String>,
    val block_id: Int
)
