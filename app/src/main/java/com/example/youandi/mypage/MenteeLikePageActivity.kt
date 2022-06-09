package com.example.youandi.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import com.example.youandi.ListViewAdapter
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.R
import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.menteefragments.LikeListViewAdpater
import com.example.youandi.menteefragments.MenteeLikeListInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenteeLikePageActivity : AppCompatActivity() {

    private val mentorDataList= mutableListOf<MentorLoginData>()

    lateinit var likeListViewAdapter: LikeListViewAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentee_like_page)

        val likeListView = findViewById<ListView>(R.id.likeList)
        likeListViewAdapter = LikeListViewAdpater(mentorDataList)
        likeListView.adapter=likeListViewAdapter


        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MenteeLikeListInterface::class.java)


        service.getMentor(myClass.user_id).enqueue(object : Callback<List<MentorLoginData>> {
            override fun onResponse(
                call: Call<List<MentorLoginData>>,
                response: Response<List<MentorLoginData>>
            ) {
                val result : List<MentorLoginData>? =  response.body()

                if(result?.size!=0){
                    for (Mentor in result!!) {
                        mentorDataList.add(Mentor)
                    }
                }else if(result?.size==0){
                    val atext = findViewById<TextView>(R.id.okid)
                    atext.text = "좋아요한 멘토가 없어요"
                }


                likeListViewAdapter.notifyDataSetChanged()
                Log.d("결과", mentorDataList.toString())

            }

            override fun onFailure(call: Call<List<MentorLoginData>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })

        val list = findViewById<ListView>(R.id.likeList)
        list.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, MentorDataActivity::class.java)

            intent.putExtra("mentor_id",mentorDataList[i].user_id.toString())
            intent.putExtra("mentor_name",mentorDataList[i].user_name.toString())
            intent.putExtra("mentor_company",mentorDataList[i].user_company.toString())
            intent.putExtra("mentor_school",mentorDataList[i].user_school.toString())
            intent.putExtra("mentor_subject",mentorDataList[i].user_subject.toString())
            intent.putExtra("mentor_grade",mentorDataList[i].user_grade.toString())
            intent.putExtra("mentor_short",mentorDataList[i].user_short)
            intent.putExtra("mentor_long",mentorDataList[i].user_long)
            intent.putExtra("mentor_profile",mentorDataList[i].user_profile)

            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenteeNaviMainActivity::class.java)
        startActivity(intent)

    }
}