package com.example.youandi.mentorchat

import com.example.youandi.menteechat.ChatData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface mentorchatInterface {
    @FormUrlEncoded
    @POST("/mentors/join/chat")
    fun getChat(
        @Field("mentor") mentor_id : String,
        @Field("mentee") mentee_id : String,
        @Field("text") user_msg : String,
    ): Call<ResponseBody>
}