package com.knear.android.provider.response.viewcontractstatechanges

data class Result(
    val block_hash: String = "",
    val changes: List<Change> = listOf()
)
