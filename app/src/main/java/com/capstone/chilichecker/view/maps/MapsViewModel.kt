package com.capstone.chilichecker.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.repository.MainRepository

class MapsViewModel(private val repository: MainRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}