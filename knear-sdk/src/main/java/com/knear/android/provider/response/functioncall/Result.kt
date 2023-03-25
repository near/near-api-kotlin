package com.knear.android.provider.response.functioncall
import com.google.gson.annotations.SerializedName

data class Result (
    @SerializedName("block_hash"   ) var blockHash   : String?           = null,
    @SerializedName("block_height" ) var blockHeight : Long?              = null,
    @SerializedName("logs"         ) var logs        : ArrayList<String> = arrayListOf(),
    @SerializedName("result"       ) var result      : ByteArray?    = null,
    @SerializedName("error"        ) var error       : String?         = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Result

        if (blockHash != other.blockHash) return false
        if (blockHeight != other.blockHeight) return false
        if (logs != other.logs) return false
        if (result != null) {
            if (other.result == null) return false
            if (!result.contentEquals(other.result)) return false
        } else if (other.result != null) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = blockHash?.hashCode() ?: 0
        result1 = 31 * result1 + (blockHeight?.hashCode() ?: 0)
        result1 = 31 * result1 + logs.hashCode()
        result1 = 31 * result1 + (result?.contentHashCode() ?: 0)
        result1 = 31 * result1 + (error?.hashCode() ?: 0)
        return result1
    }
}
