package com.capstone.chilichecker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.pref.UserPreference
import com.capstone.chilichecker.data.remote.retrofit.ApiService
import com.capstone.chilichecker.di.Event
import kotlinx.coroutines.flow.Flow

class MainRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ) = MainRepository(userPreference, apiService)
    }

}