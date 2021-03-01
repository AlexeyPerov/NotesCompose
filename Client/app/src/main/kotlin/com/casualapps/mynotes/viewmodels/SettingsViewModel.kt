package com.casualapps.mynotes.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.repo.auth.AuthStateRepository
import com.casualapps.mynotes.data.repo.user.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val authStateRepository: AuthStateRepository
) : ViewModel() {
    private val _logoutLiveData = MutableLiveData<Boolean>()
    val logoutLiveData: LiveData<Boolean>
        get() = _logoutLiveData

    private val _userNameLiveData = MutableLiveData("")
    val userNameLiveData: LiveData<String>
        get() = _userNameLiveData

    val loginState = mutableStateOf(false)

    init {
        viewModelScope.launch {
            loginState.value = authStateRepository.getLoggedInUser() > 0L
        }
    }

    fun fetchUser() = viewModelScope.launch {
        val userId = authStateRepository.getLoggedInUser()
        _userNameLiveData.postValue(userRepository.fetchUser(userId)?.name ?: "Guest")
    }

    fun logout() = viewModelScope.launch {
        authStateRepository.logout()
        _logoutLiveData.postValue(true)
    }
}
