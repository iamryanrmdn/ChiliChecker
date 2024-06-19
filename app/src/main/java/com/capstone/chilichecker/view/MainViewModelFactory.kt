package com.capstone.chilichecker.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.chilichecker.data.repository.MainRepository
import com.capstone.chilichecker.di.Injection
import com.capstone.chilichecker.view.main.MainViewModel

class MainViewModelFactory(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(repository) as T
                }

                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }

    companion object {
        fun getInstance(context: Context) = MainViewModelFactory(Injection.provideMainRepository(context))
    }
}