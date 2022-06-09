package com.example.youandi.mypage

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface fileEditInterface {
    @Multipart
    @POST("mentors/join/modifyProfile")
    fun modify(
        @Part("mentor") user_id: RequestBody,
        @Part uploadProfile: MultipartBody.Part
//        @Part uploadGraduationFile: MultipartBody.Part,
//        @Part uploadCompanyFile: MultipartBody.Part
    ): Call<String>
}