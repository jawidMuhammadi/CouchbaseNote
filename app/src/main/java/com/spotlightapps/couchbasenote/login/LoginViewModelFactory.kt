package com.spotlightapps.couchbasenote.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spotlightapps.couchbasenote.AppRepository

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */
class LoginViewModelFactory(
    private val appRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(appRepository) as T
    }
}