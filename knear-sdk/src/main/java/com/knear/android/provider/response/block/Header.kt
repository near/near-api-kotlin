package com.knear.android.provider.response.block

import com.google.gson.annotations.SerializedName


data class Header(

    @SerializedName("approvals") var approvals: ArrayList<String> = arrayListOf(),
    @SerializedName("block_merkle_root") var blockMerkleRoot: String? = null,
    @SerializedName("block_ordinal") var blockOrdinal: Int? = null,
    @SerializedName("challenges_result") var challengesResult: ArrayList<String> = arrayListOf(),
    @SerializedName("challenges_root") var challengesRoot: String? = null,
    @SerializedName("chunk_headers_root") var chunkHeadersRoot: String? = null,
    @SerializedName("chunk_mask") var chunkMask: ArrayList<Boolean> = arrayListOf(),
    @SerializedName("chunk_receipts_root") var chunkReceiptsRoot: String? = null,
    @SerializedName("chunk_tx_root") var chunkTxRoot: String? = null,
    @SerializedName("chunks_included") var chunksIncluded: Int? = null,
    @SerializedName("epoch_id") var epochId: String? = null,
    @SerializedName("epoch_sync_data_hash") var epochSyncDataHash: String? = null,
    @SerializedName("gas_price") var gasPrice: String? = null,
    @SerializedName("hash") var hash: String = "",
    @SerializedName("height") var height: Int? = null,
    @SerializedName("last_ds_final_block") var lastDsFinalBlock: String? = null,
    @SerializedName("last_final_block") var lastFinalBlock: String? = null,
    @SerializedName("latest_protocol_version") var latestProtocolVersion: Int? = null,
    @SerializedName("next_bp_hash") var nextBpHash: String? = null,
    @SerializedName("next_epoch_id") var nextEpochId: String? = null,
    @SerializedName("outcome_root") var outcomeRoot: String? = null,
    @SerializedName("prev_hash") var prevHash: String? = null,
    @SerializedName("prev_height") var prevHeight: Int? = null,
    @SerializedName("prev_state_root") var prevStateRoot: String? = null,
    @SerializedName("random_value") var randomValue: String? = null,
    @SerializedName("rent_paid") var rentPaid: String? = null,
    @SerializedName("signature") var signature: String? = null,
    @SerializedName("timestamp") var timestamp: Long? = null,
    @SerializedName("timestamp_nanosec") var timestampNanosec: String? = null,
    @SerializedName("total_supply") var totalSupply: String? = null,
    @SerializedName("validator_proposals") var validatorProposals: ArrayList<String> = arrayListOf(),
    @SerializedName("validator_reward") var validatorReward: String? = null

)
