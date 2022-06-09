package com.example.youandi.serverdata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface serverDataInterface {
    @GET("menteeList")
    fun getMentee(): Call<List<Mentee>>
}