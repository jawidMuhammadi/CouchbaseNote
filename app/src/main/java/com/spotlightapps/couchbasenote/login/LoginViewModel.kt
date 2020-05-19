package com.spotlightapps.couchbasenote.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.Event

class LoginViewModel(private val appRepository: AppRepository) : ViewModel() {

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

    fun registerUser(userName: String, password: String){

    }
}

