package com.example.youandi.mypage

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface introEditInterface {
    @Multipart
    @POST("mentors/join/modifyInfo")
    fun modify(
        @Part("mentor") user_id: RequestBody,
        @Part("password") user_pwd: RequestBody,
        @Part("name") user_name: RequestBody,
        @Part("school") user_school: RequestBody,
        @Part("grade") user_grade: RequestBody,
        @Part("subject") user_subject: RequestBody,
        @Part("company") user_company: RequestBody,
        @Part("shortIntroduce") user_shortIntro: RequestBody,
        @Part("longIntroduce") user_longIntro: RequestBody
    ): Call<String>
}