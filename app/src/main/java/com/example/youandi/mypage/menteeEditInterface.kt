package com.example.youandi.mypage

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface menteeEditInterface {
    @Multipart
    @POST("mentees/join/modify")
    fun modify(
        @Part("mentee") user_id: RequestBody,
        @Part("password") user_pwd: RequestBody,
        @Part("name") user_name: RequestBody,
        @Part("school") user_school: RequestBody,
        @Part("grade") user_grade: RequestBody,
        @Part("subject") user_subject: RequestBody,
        @Part uploadProfile: MultipartBody.Part
    ): Call<String>
}