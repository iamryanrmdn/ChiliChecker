package com.capstone.chilichecker.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.repository.PredictRepository

class DetailViewModel(private val predictRepository: PredictRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return predictRepository.getSession().asLiveData()
    }
}