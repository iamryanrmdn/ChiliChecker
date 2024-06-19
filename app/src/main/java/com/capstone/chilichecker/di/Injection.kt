package com.capstone.chilichecker.di

import android.content.Context
import com.capstone.chilichecker.data.pref.UserPreference
import com.capstone.chilichecker.data.pref.dataStore
import com.capstone.chilichecker.data.remote.retrofit.ApiConfig
import com.capstone.chilichecker.data.repository.MainRepository
import com.capstone.chilichecker.data.repository.PredictRepository
import com.capstone.chilichecker.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(userPreference, apiService)
    }

    fun provideMainRepository(context: Context): MainRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return MainRepository.getInstance(preference, apiService)
    }

    fun providePredictRepository(context: Context): PredictRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return PredictRepository.getInstance(preference, apiService)
    }
}