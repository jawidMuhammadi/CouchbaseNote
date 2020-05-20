package com.spotlightapps.couchbasenote.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.Event
import com.spotlightapps.couchbasenote.data.UserProfile
import kotlinx.coroutines.*

class LoginViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var _onSignUpClicked = MutableLiveData<Event<Unit>>()
    val onSignUpClicked: LiveData<Event<Unit>> = _onSignUpClicked

    private var _onLoginClicked = MutableLiveData<Event<Unit>>()
    val onLoginClicked: LiveData<Event<Unit>> = _onLoginClicked

    fun onSignUpClicked() {
        _onSignUpClicked.value = Event(Unit)
    }

    fun onLoginButtonClicked() {
        _onLoginClicked.value = Event(Unit)
    }

    fun saveUser(userName: String, password: String) {
        uiScope.launch {
            appRepository.saveUser(userName, password)
        }
    }

    fun getUser(userName: String): UserProfile? {
        return appRepository.getUser(userName)
    }

    override fun onCleared() {
        super.onCleared()
        appRepository.databaseManager.closeDatabaseForUser()
        job.cancel()
    }
}

