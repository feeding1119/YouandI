package com.example.youandi.menteechat

import com.google.gson.annotations.SerializedName

data class ChatData (
    @SerializedName("chat_num")
    val chat_num : Long,

    @SerializedName("sender_index")
    val sender_index: Long,

    @SerializedName("receiver_index")
    val receiver_index: Long,

    @SerializedName("text")
    val text: String,

    @SerializedName("date")
    val date: String
    )