package com.knear.android.provider.response.blockchanges

data class Result(
    val block_hash: String = "",
    val changes: List<Change> = listOf()
)
