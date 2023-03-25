package com.knear.android.user

import com.knear.android.user.domain.model.UserInformation

interface NearUserInformationListener {

    fun userInformationResponse(userInformation: UserInformation)
}