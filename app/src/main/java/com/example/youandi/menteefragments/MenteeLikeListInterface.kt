package com.example.youandi.menteefragments

import com.example.youandi.auth.MentorLoginData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MenteeLikeListInterface {
    @GET("/mentees/likeList")
    fun getMentor(
        @Query("mentee") mentee : String
    ) : Call<List<MentorLoginData>>
}