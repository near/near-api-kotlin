package com.knear.android.user.view

import android.app.Application
import androidx.lifecycle.*
import com.knear.android.user.data.entities.UserInformationEntity
import com.knear.android.user.domain.model.UserInformation
import com.knear.android.user.domain.usecase.GetAllUsersUseCase
import com.knear.android.user.domain.usecase.RemoveUserSessionUseCase
import com.knear.android.user.domain.usecase.SaveUserSessionUseCase
import kotlinx.coroutines.launch


class NearUserViewModel(application: Application) : AndroidViewModel(application) {

    private val saveUserSessionUseCase = SaveUserSessionUseCase(application)
    private val getAllUsersUseCase = GetAllUsersUseCase(application)
    private val removeUserSessionUseCase = RemoveUserSessionUseCase(application)

    val getAllUsers: LiveData<List<UserInformationEntity>> = getAllUsersUseCase.getAllUsers().asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun saveUserInformation(userInformation: UserInformation) = viewModelScope.launch {
        saveUserSessionUseCase.saveUserSession(userInformation)
    }

    fun removeUserInformation() = viewModelScope.launch {
        removeUserSessionUseCase.removeUserInformation()
    }
}

class NearUserViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NearUserViewModel(mApplication) as T
    }
}