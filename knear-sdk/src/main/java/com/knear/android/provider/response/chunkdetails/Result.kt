package com.knear.android.provider.response.chunkdetails

data class Result(
    val author: String = "",
    var header: Header? = null,
    val receipts: List<Any> = listOf(),
    val transactions: List<Any> = listOf()
)
