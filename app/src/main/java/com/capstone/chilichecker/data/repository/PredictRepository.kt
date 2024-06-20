package com.capstone.chilichecker.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.pref.UserPreference
import com.capstone.chilichecker.data.remote.response.PredictResponse
import com.capstone.chilichecker.data.remote.retrofit.ApiConfig
import com.capstone.chilichecker.data.remote.retrofit.ApiService
import com.capstone.chilichecker.di.Event
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class PredictRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _predictResponse = MutableLiveData<PredictResponse>()
    val predictResponse: LiveData<PredictResponse> = _predictResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun uploadImage(imageFile: File) {
        _isLoading.value = true
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )
        Log.d("Test Image", multipartBody.toString())

        val client = apiService.uploadImage(multipartBody)
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    _predictResponse.value = responseBody!!
                } else {
                    _toastText.value = Event("Error : ${response.message()}")
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = PredictRepository(userPreference, apiService)
    }
}