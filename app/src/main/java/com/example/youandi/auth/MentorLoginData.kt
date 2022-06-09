package com.example.youandi.auth

import com.google.gson.annotations.SerializedName

class MentorLoginData (

    @SerializedName("id")
    val user_id: String,

    @SerializedName("name")
    val user_name: String,

    @SerializedName("school")
    val user_school: String,

    @SerializedName("grade")
    val user_grade: Float,

    @SerializedName("subject")
    val user_subject: String,

    @SerializedName("profileFilePath")
    val user_profile: String,

    @SerializedName("company")
    val user_company: String,

    @SerializedName("shortIntroduce")
    val user_short: String,

    @SerializedName("longIntroduce")
    val user_long: String,

    @SerializedName("pass")
    val user_pass: Boolean,

    @SerializedName("index")
    val user_index: Long,

    @SerializedName("status")
    val user_status: Boolean,

    @SerializedName("text")
    val last_text: String
)