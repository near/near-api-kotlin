package com.knear.android.user.domain.usecase

import android.app.Application
import com.knear.android.user.data.repositories.UserSessionRepository

class GetAllUsersUseCase(application: Application) {

    private val nearUserRepository = UserSessionRepository(application)

    fun getAllUsers() = nearUserRepository.getAllUsers
}