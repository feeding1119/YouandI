package com.example.youandi.menteefragments

import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.serverdata.Mentor
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MentorMatchingInterface {
    @GET("/mentorsMatchingList")
    fun getMentor(
        @Query("mentee") user_id : String,
        @Query("school") user_school : String,
        @Query("grade") user_grade: Float,
        @Query("subject") user_subject: String
    ) : Call<List<MentorLoginData>>
}