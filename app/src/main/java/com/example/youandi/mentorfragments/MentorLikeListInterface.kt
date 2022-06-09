package com.example.youandi.mentorfragments

import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.auth.MentorLoginData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MentorLikeListInterface {
    @GET("/mentors/likedList")
    fun getMentee(
        @Query("mentor") mentor : String
    ) : Call<List<MenteeLoginData>>
}