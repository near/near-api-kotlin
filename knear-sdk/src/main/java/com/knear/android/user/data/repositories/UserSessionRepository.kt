package com.knear.android.user.data.repositories

import android.app.Application
import androidx.annotation.WorkerThread
import com.knear.android.user.data.database.NearDatabase
import com.knear.android.user.data.entities.UserInformationEntity
import com.knear.android.user.domain.model.UserInformation
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UserSessionRepository(application: Application) {

    private val nearDatabase = NearDatabase.getDatabase(application)
    private val userDao = nearDatabase.userDao()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val getAllUsers: Flow<List<UserInformationEntity>> = userDao.getUserInformation()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveUserSession(userInformation: UserInformation) {
        userDao.saveUserInformation(getUserInformationEntity(userInformation))
    }

    private fun getUserInformationEntity(userInformation: UserInformation)  =
        UserInformationEntity(
            accountId = userInformation.accountId,
            publicKey = userInformation.publicKey,
            isLoggedIn = userInformation.isLoggedIn
        )

    suspend fun removeUserInformation() {
        userDao.deleteAll()
    }
}