package com.example.youandi.mentorchat

import com.example.youandi.menteechat.ChatData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface mentorChatDataInterface {
    @GET("/mentors/join/chat")
    fun getChatData(
        @Query("mentor") mentor : String
    ): Call<List<ChatData>>
}