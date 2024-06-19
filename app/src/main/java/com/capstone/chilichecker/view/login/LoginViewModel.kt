package com.capstone.chilichecker.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.remote.response.LoginResponse
import com.capstone.chilichecker.data.repository.UserRepository
import com.capstone.chilichecker.di.Event
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    val loginResponse: LiveData<LoginResponse> = repository.loginResponse

    val isLoading: LiveData<Boolean> = repository.isLoading

    val toastText: LiveData<Event<String>> = repository.toastText

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}