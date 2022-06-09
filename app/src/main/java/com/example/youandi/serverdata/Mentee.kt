package com.example.youandi.serverdata

import com.google.gson.annotations.SerializedName

data class Mentee(
    @SerializedName("id")
    val user_id : String,

    @SerializedName("password")
    val user_pwd: String,

    @SerializedName("name")
    val user_name: String,

    @SerializedName("school")
    val user_school: String,

    @SerializedName("grade")
    val user_grade: String,

    @SerializedName("subject")
    val user_subject: String,

    @SerializedName("profileFilePath")
    val user_profile: String,

    @SerializedName("index")
    val user_index: Long
)