package com.example.youandi.auth

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface retrofit_interface {
    // api 를 관리해 주는 인터페이스
    // 프로필 이미지 보내기
    @Multipart
    @POST("/mentees/new")
    fun post_Porfile_Request(
        @Part ("ID") user_id : RequestBody,
        @Part ("password") user_pwd : RequestBody,
        @Part ("name") user_name : RequestBody,
        @Part ("school") user_school : RequestBody,
        @Part ("grade") user_grade : RequestBody,
        @Part ("subject") user_subject : RequestBody,
        @Part uploadProfile : MultipartBody.Part

    ): Call<String>
}