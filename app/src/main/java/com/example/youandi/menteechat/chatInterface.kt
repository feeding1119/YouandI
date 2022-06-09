package com.example.youandi.menteechat

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface chatInterface {
    @FormUrlEncoded
    @POST("/mentees/join/chat")
    fun getChat(
        @Field("mentee") mentee_id : String,
        @Field("mentor") mentor_id : String,
        @Field("text") user_msg : String,
    ): Call<ResponseBody>
}