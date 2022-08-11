package com.knear.android.provider.response.viewaccesskeylist

data class Result(
    var block_hash: String? = null,
    var block_height: Long = 0L,
    var keys: List<Key> = listOf()
)
