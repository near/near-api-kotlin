package com.knear.android.provider.response.viewaccesskeychangeall

data class Result(
    val block_hash: String = "",
    val changes: List<Change> = listOf()
)
