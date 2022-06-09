//package com.example.youandi
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.ListView
//import com.example.youandi.serverdata.Mentee
//import com.example.youandi.serverdata.serverDataInterface
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class MainActivity : AppCompatActivity() {
//
//    private val userDataList= mutableListOf<Mentee>()
//
//    lateinit var listViewAdapter: ListViewAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val userListView = findViewById<ListView>(R.id.mainListView)
//        listViewAdapter = ListViewAdapter(userDataList)
//        userListView.adapter = listViewAdapter
//
//
//        //서버 연결
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://34.125.157.72:8080/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service = retrofit.create(serverDataInterface::class.java)
//
//
//        service.getMentee().enqueue(object : Callback<List<Mentee>> {
//            override fun onResponse(
//                call: Call<List<Mentee>>,
//                response: Response<List<Mentee>>
//            ) {
//                val result : List<Mentee>? =  response.body()
//                for (Mentee in result!!) {
//                    userDataList.add(Mentee)
//                }
//                listViewAdapter.notifyDataSetChanged()
//                Log.d("결과", userDataList.toString())
//
//            }
//
//            override fun onFailure(call: Call<List<Mentee>>, t: Throwable) {
//                Log.d("결과:", "실패 : $t")
//            }
//        })
//
//
//    }
//}