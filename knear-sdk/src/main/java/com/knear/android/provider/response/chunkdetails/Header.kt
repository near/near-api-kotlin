package com.knear.android.provider.response.chunkdetails

data class Header(
    val balance_burnt: String,
    val chunk_hash: String,
    val encoded_length: Int,
    val encoded_merkle_root: String,
    val gas_limit: Long,
    val gas_used: Int,
    val height_created: Int,
    val height_included: Int,
    val outcome_root: String,
    val outgoing_receipts_root: String,
    val prev_block_hash: String,
    val prev_state_root: String,
    val rent_paid: String,
    val shard_id: Int,
    val signature: String,
    val tx_root: String,
    val validator_proposals: List<Any>,
    val validator_reward: String
)
