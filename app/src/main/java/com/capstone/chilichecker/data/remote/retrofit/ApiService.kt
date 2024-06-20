package com.capstone.chilichecker.data.remote.retrofit

import com.capstone.chilichecker.data.remote.response.BookmarkDeleteResponse
import com.capstone.chilichecker.data.remote.response.BookmarkListResponse
import com.capstone.chilichecker.data.remote.response.BookmarkResponse
import com.capstone.chilichecker.data.remote.response.LoginResponse
import com.capstone.chilichecker.data.remote.response.PredictResponse
import com.capstone.chilichecker.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<PredictResponse>

    @Multipart
    @POST("bookmark")
    suspend fun savePredict(
        @Part file: MultipartBody.Part
    ): Call<BookmarkResponse>

    @GET("bookmarklist")
    suspend fun getBookmark(
    ): Call<BookmarkListResponse>

    @DELETE("bookmarkdelete/{id}")
    suspend fun deleteBookmark(
        @Path("id") bookmarkId: String
    ): Call<BookmarkDeleteResponse>
}