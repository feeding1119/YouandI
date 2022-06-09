package com.example.youandi.menteefragments

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MenteeUnlikeInterface {
    @FormUrlEncoded
    @POST("/mentees/join/unlike")
    fun menteeUnlike(
        @Field("mentee") mentee_id: String,
        @Field("mentor") mentor_id: String
    ) : Call<ResponseBody>
}