package com.example.youandi.mentorchat

import com.example.youandi.menteechat.ChatData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface adminchatInterface {
    @GET("/mentors/join/adminChat")
    fun getChatData(
        @Query("mentor") mentor : String
    ): Call<List<ChatData>>
}