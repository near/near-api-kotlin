package com.knear.android.provider.response.viewcontractstate

data class Result(
    val block_hash: String = "",
    val block_height: Int = 0,
    val proof: List<Any> = listOf(),
    val values: List<Value> = listOf()
)
