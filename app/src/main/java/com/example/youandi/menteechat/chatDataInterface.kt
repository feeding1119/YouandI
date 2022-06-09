package com.example.youandi.menteechat

import retrofit2.Call
import retrofit2.http.*

interface chatDataInterface {
    @GET("/mentees/join/chat")
    fun getChatData(
        @Query("mentee") mentee : String
    ): Call<List<ChatData>>
}