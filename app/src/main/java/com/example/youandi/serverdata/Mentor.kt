package com.example.youandi.serverdata

import com.google.gson.annotations.SerializedName

class Mentor (
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

    @SerializedName("graduationFilePath")
    val user_graduationFile: String,

    @SerializedName("companyFilePath")
    val user_companyFile: String,

    @SerializedName("company")
    val user_company: String,

    @SerializedName("shortIntroduce")
    val user_short: String,

    @SerializedName("longIntroduce")
    val user_long: String,


    @SerializedName("index")
    val user_index: Long
)