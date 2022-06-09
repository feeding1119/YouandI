package com.example.youandi.auth

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MenteeLoginService {
    @FormUrlEncoded
    @POST("mentees/join")
    fun loginRequest(
        @Field("ID") user_id : String,
        @Field("password") user_pwd:String
    ) : Call<MenteeLoginData>
}