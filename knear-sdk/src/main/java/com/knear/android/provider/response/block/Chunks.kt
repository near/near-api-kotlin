package com.knear.android.provider.response.block

import com.google.gson.annotations.SerializedName


data class Chunks(

    @SerializedName("balance_burnt") var balanceBurnt: String? = null,
    @SerializedName("chunk_hash") var chunkHash: String? = null,
    @SerializedName("encoded_length") var encodedLength: Int? = null,
    @SerializedName("encoded_merkle_root") var encodedMerkleRoot: String? = null,
    @SerializedName("gas_limit") var gasLimit: Long? = null,
    @SerializedName("gas_used") var gasUsed: Long? = null,
    @SerializedName("height_created") var heightCreated: Int? = null,
    @SerializedName("height_included") var heightIncluded: Int? = null,
    @SerializedName("outcome_root") var outcomeRoot: String? = null,
    @SerializedName("outgoing_receipts_root") var outgoingReceiptsRoot: String? = null,
    @SerializedName("prev_block_hash") var prevBlockHash: String? = null,
    @SerializedName("prev_state_root") var prevStateRoot: String? = null,
    @SerializedName("rent_paid") var rentPaid: String? = null,
    @SerializedName("shard_id") var shardId: Int? = null,
    @SerializedName("signature") var signature: String? = null,
    @SerializedName("tx_root") var txRoot: String? = null,
    @SerializedName("validator_proposals") var validatorProposals: ArrayList<String> = arrayListOf(),
    @SerializedName("validator_reward") var validatorReward: String? = null

)
