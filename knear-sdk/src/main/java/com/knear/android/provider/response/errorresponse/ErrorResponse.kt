package com.knear.android.provider.response.errorresponse

data class ErrorResponse(
    val cause: Any = {},
    val code: Int = 0,
    val data: Any = {},
    val message: String = "",
    val name: String = ""
)
