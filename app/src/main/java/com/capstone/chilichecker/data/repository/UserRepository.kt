package com.capstone.chilichecker.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.chilichecker.data.pref.UserModel
import com.capstone.chilichecker.data.pref.UserPreference
import com.capstone.chilichecker.data.remote.response.LoginResponse
import com.capstone.chilichecker.data.remote.response.RegisterResponse
import com.capstone.chilichecker.data.remote.retrofit.ApiService
import com.capstone.chilichecker.di.Event
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                try {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _registerResponse.value = response.body()
                        _toastText.value = Event(response.body()?.message.toString())
                    } else {
                        val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                        val error = jsonObject?.getBoolean("error")
                        val message = jsonObject?.getString("message")
                        _registerResponse.value = RegisterResponse(error, message)
                        _toastText.value = Event(
                            "${response.message()} ${response.code()}, $message"
                        )
                        Log.e(
                            "Register",
                            "onResponse: ${response.message()}, ${response.code()} $message"
                        )
                    }
                } catch (e: JSONException) {
                    _toastText.value = Event(e.message.toString())
                    Log.e("JSONException", "onResponse: ${e.message.toString()}")
                } catch (e: Exception) {
                    Log.e("Exception", "onResponse: ${e.message.toString()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _toastText.value = Event("No Internet Connection")
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _toastText.value = Event(t.message.toString())
                        Log.e("postRegister", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                    val error = jsonObject?.getBoolean("error")
                    val message = jsonObject?.getString("message")
                    _loginResponse.value = LoginResponse(null, error, message)
                    _toastText.value = Event("${response.message()} ${response.code()}, $message")
                    Log.e("Login", "onResponse: ${response.message()}, ${response.code()} $message")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }

}