package com.knear.android.user.domain.usecase

import android.app.Application
import com.knear.android.user.data.repositories.UserSessionRepository

class RemoveUserSessionUseCase(val application: Application) {

    private val nearUserRepository = UserSessionRepository(application)

    suspend fun removeUserInformation() {
        nearUserRepository.removeUserInformation()
    }
}