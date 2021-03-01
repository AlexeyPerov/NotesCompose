package com.casualapps.mynotes.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casualapps.mynotes.data.entities.User
import com.casualapps.mynotes.data.repo.auth.AuthStateRepository
import com.casualapps.mynotes.data.repo.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val userRepo: UserRepository, private val authStateRepository: AuthStateRepository) : ViewModel() {

    private val _loginResultLiveData = MutableLiveData<Long>()

    private val _userNameErrorLiveData = MutableLiveData<String?>()
    private val _passwordErrorLiveData = MutableLiveData<String?>()

    val userNameErrorLiveData: LiveData<String?>
        get() = _userNameErrorLiveData

    val passwordErrorLiveData: LiveData<String?>
        get() = _passwordErrorLiveData

    val loginResultLiveData: LiveData<Long>
        get() = _loginResultLiveData

    fun login(username: String, password: String) = viewModelScope.launch {
        _userNameErrorLiveData.postValue(null)
        _passwordErrorLiveData.postValue(null)
        if (username.isEmpty()) {
            _userNameErrorLiveData.postValue("Username cannot be empty!")
            return@launch
        }
        if (password.isEmpty()) {
            _passwordErrorLiveData.postValue("Password cannot be empty!")
            return@launch
        }
        val user = User(username, password)
        val loggedInUserId = userRepo.isValidUser(user = user)
        if (loggedInUserId != -1L) {
            authStateRepository.login(loggedInUserId)
        }
        _loginResultLiveData.postValue(loggedInUserId)
    }
}
