package com.capstone.chilichecker.view.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.remote.response.PredictResponse
import com.capstone.chilichecker.data.repository.PredictRepository
import kotlinx.coroutines.launch
import java.io.File

class ScanViewModel(private val predictRepository: PredictRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = predictRepository.isLoading

    val uploadImageResponse: LiveData<PredictResponse> = predictRepository.predictResponse

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            predictRepository.uploadImage(imageFile)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return predictRepository.getSession().asLiveData()
    }
}