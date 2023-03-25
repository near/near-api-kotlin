package com.knear.android.user.domain.model

data class UserInformation(
    var accountId:String = "",
    var publicKey:String = "",
    var isLoggedIn:Boolean = false
)
