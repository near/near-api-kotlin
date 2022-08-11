package com.knear.android.provider.response.networkstatus

data class SyncInfo(
    val latest_block_hash: String,
    val latest_block_height: Int,
    val latest_block_time: String,
    val latest_state_root: String,
    val syncing: Boolean
)
