package com.knear.android.user.domain.usecase

import android.app.Application
import com.knear.android.user.data.repositories.UserSessionRepository
import com.knear.android.user.domain.model.UserInformation

class SaveUserSessionUseCase(val application: Application) {

    private val nearUserRepository = UserSessionRepository(application)

    suspend fun saveUserSession(userInformation: UserInformation) = nearUserRepository.saveUserSession(userInformation)
}