package com.knear.android.provider.response.viewaccesskeychanges

data class Result(
    var block_hash: String? = null,
    var changes: List<Change> = listOf()
)
