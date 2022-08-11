package com.knear.android.provider.response.protocolconfig

data class AccountCreationConfig(
    val min_allowed_top_level_account_length: Int,
    val registrar_account_id: String
)
