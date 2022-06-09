package com.example.youandi.mentorchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.youandi.R
import com.example.youandi.menteechat.ChatData
import com.example.youandi.menteechat.ChatListViewAdapter
import com.example.youandi.mypage.myClass
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import kotlin.concurrent.timer

class MenChatActivity : AppCompatActivity() {

    private val chatDataList= mutableListOf<ChatData>()
    lateinit var chatListViewAdapter: ChatListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_men_chat)

        val chatUserListView = findViewById<ListView>(R.id.msgList)
        chatListViewAdapter = ChatListViewAdapter(this,chatDataList)
        chatUserListView.adapter = chatListViewAdapter

        val topName = findViewById<TextView>(R.id.textArea)
        var mName= intent.getStringExtra("mentee_name")
        topName.text="$mName 님과의 메세지"

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
        val sendService = retrofit.create(mentorchatInterface::class.java)
        val getService = retrofit.create(mentorChatDataInterface::class.java)
        var user_id = intent.getStringExtra("user_id")

        timer(period = 1000,initialDelay = 1000){
            getService.getChatData(myClass.user_id).enqueue(object : Callback<List<ChatData>> {
                override fun onResponse(
                    call: Call<List<ChatData>>,
                    response: Response<List<ChatData>>
                ) {

                    chatDataList.clear()

                    val result : List<ChatData>? = response.body()
                    for (chatData in result!!) {
                        if(chatData.sender_index==myClass.user_index&&chatData.receiver_index==myClass.other_index){
                            chatDataList.add(chatData)
                        }else if(chatData.sender_index==myClass.other_index&&chatData.receiver_index==myClass.user_index){
                            chatDataList.add(chatData)
                        }
                        Log.d("데이터결과", chatDataList.toString())
                    }
                    chatListViewAdapter.notifyDataSetChanged()
                    Log.d("결과", response.body().toString())

                }override fun onFailure(call: Call<List<ChatData>>, t: Throwable) {
                    Log.e("연결에러:", "실패 : $t")
                }
            })

        }


        val testBtn = findViewById<Button>(R.id.testBtn)
        testBtn.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.chat_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("메세지 보내기")

            val mAlertDialog = mBuilder.show()

            val btn = mAlertDialog.findViewById<Button>(R.id.sendBtn)


            btn?.setOnClickListener {

                var menteeId = intent.getStringExtra("mentee_id")
                val sendText = mAlertDialog.findViewById<EditText>(R.id.sendTextArea)?.text.toString()

                sendService.getChat(myClass.user_id,menteeId!!,sendText).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.d("결과:", "쪽지보내기")
                    }override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("결과:", "실패 : $t")
                    }
                })

                mAlertDialog.dismiss()
            }

        }
    }
}