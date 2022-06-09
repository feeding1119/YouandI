package com.example.youandi.menteefragments

import com.example.youandi.auth.MentorLoginData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MenteeLikeInterface {
    @FormUrlEncoded
    @POST("/mentees/join/like")
    fun menteeLike(
        @Field("mentee") mentee_id: String,
        @Field("mentor") mentor_id: String
    ) : Call<ResponseBody>
}