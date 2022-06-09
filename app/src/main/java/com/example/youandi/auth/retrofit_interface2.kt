package com.example.youandi.auth

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface retrofit_interface2 {
    // api 를 관리해 주는 인터페이스
    // 프로필 이미지 보내기
    @Multipart
    @POST("mentors/new")
    fun post_Porfile_Request(
        @Part ("ID") user_id: RequestBody,
        @Part ("password") user_pwd: RequestBody,
        @Part ("name") user_name: RequestBody,
        @Part ("school") user_school: RequestBody,
        @Part ("grade") user_grade: RequestBody,
        @Part ("subject") user_subject: RequestBody,
        @Part ("company") user_company: RequestBody,
        @Part("shortIntroduce") user_shortIntro: RequestBody,
        @Part("longIntroduce") user_longIntro: RequestBody,
        @Part uploadProfile: MultipartBody.Part,
        @Part uploadGraduationFile: MultipartBody.Part,
        @Part uploadCompanyFile: MultipartBody.Part
    ): Call<String>
}