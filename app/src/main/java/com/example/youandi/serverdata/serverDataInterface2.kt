package com.example.youandi.serverdata

import com.example.youandi.auth.MentorLoginData
import retrofit2.Call
import retrofit2.http.GET

interface serverDataInterface2 {
    @GET("mentorList")
    fun getMentor(): Call<List<MentorLoginData>>}