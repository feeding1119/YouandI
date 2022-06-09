package com.example.youandi.mentorchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import com.example.youandi.R
import com.example.youandi.menteechat.ChatData
import com.example.youandi.menteechat.ChatListViewAdapter
import com.example.youandi.mypage.myClass
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import kotlin.concurrent.timer

class AdminChatActivity : AppCompatActivity() {

    private val chatDataList= mutableListOf<ChatData>()
    lateinit var chatListViewAdapter: ChatListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_chat)


        val chatUserListView = findViewById<ListView>(R.id.msgList)
        chatListViewAdapter = ChatListViewAdapter(this,chatDataList)
        chatUserListView.adapter = chatListViewAdapter

        val topName = findViewById<TextView>(R.id.textArea)
        topName.text="관리자님의 메세지"

        //서버 연결
        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()

        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl("http://34.125.157.72:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val getService = retrofit.create(adminchatInterface::class.java)

        timer(period = 1000,initialDelay = 1000){
            getService.getChatData(myClass.user_id).enqueue(object : Callback<List<ChatData>> {
                override fun onResponse(
                    call: Call<List<ChatData>>,
                    response: Response<List<ChatData>>
                ) {

                    chatDataList.clear()

                    val result : List<ChatData>? = response.body()
                    for (chatData in result!!) {
                        chatDataList.add(chatData)
                        Log.d("데이터결과", chatDataList.toString())
                    }
                    chatListViewAdapter.notifyDataSetChanged()
                    Log.d("결과", response.body().toString())

                }override fun onFailure(call: Call<List<ChatData>>, t: Throwable) {
                    Log.e("연결에러:", "실패 : $t")
                }
            })

        }
    }
}