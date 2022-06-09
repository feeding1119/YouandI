package com.example.youandi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.youandi.serverdata.Mentee
import com.example.youandi.serverdata.Mentor
import com.example.youandi.serverdata.serverDataInterface
import com.example.youandi.serverdata.serverDataInterface2
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class MenteeMainActivity : AppCompatActivity() {
//
//    private val mentorDataList= mutableListOf<Mentor>()
//
//    lateinit var cardStackAdapter: CardStackAdapter
//    lateinit var manager: CardStackLayoutManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_mentee_main)
//
//        //카드 스택뷰
//        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)
//
//        manager = CardStackLayoutManager(baseContext,object :CardStackListener{
//            override fun onCardDragging(direction: Direction?, ratio: Float) {
//
//            }
//
//            override fun onCardSwiped(direction: Direction?) {
//
//            }
//
//            override fun onCardRewound() {
//
//            }
//
//            override fun onCardCanceled() {
//
//            }
//
//            override fun onCardAppeared(view: View?, position: Int) {
//
//            }
//
//            override fun onCardDisappeared(view: View?, position: Int) {
//
//            }
//
//        })
//
//        //서버 연결
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://34.125.157.72:8080/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service = retrofit.create(serverDataInterface2::class.java)
//
//
//        service.getMentor().enqueue(object : Callback<List<Mentor>> {
//            override fun onResponse(
//                call: Call<List<Mentor>>,
//                response: Response<List<Mentor>>
//            ) {
//                val result : List<Mentor>? =  response.body()
//                for (Mentor in result!!) {
//                    mentorDataList.add(Mentor)
//                }
//                cardStackAdapter.notifyDataSetChanged()
//                Log.d("결과", mentorDataList.toString())
//
//            }
//
//            override fun onFailure(call: Call<List<Mentor>>, t: Throwable) {
//                Log.d("결과:", "실패 : $t")
//            }
//        })
//
//
//        cardStackAdapter = CardStackAdapter(baseContext, mentorDataList)
//        cardStackView.layoutManager = manager
//        cardStackView.adapter = cardStackAdapter
//    }
//}