package com.example.youandi.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.youandi.R
import com.example.youandi.menteefragments.MenteeLikeInterface
import com.example.youandi.menteefragments.MenteeUnlikeInterface
import com.example.youandi.menteefragments.MentorMatchingInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MentorDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_data)


        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val likeService = retrofit.create(MenteeLikeInterface::class.java)
        val unlikeService = retrofit.create(MenteeUnlikeInterface::class.java)

        val likebtn = findViewById<Button>(R.id.likeBtn)
        likebtn.setOnClickListener {
            likeService.menteeLike(myClass.user_id,intent.getStringExtra("mentor_id").toString())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ){
                        Log.d("결과:", "성공")
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("결과:", "실패 : $t")
                    }
                })

        }

        val cancelbtn = findViewById<Button>(R.id.cancelBtn)
        cancelbtn.setOnClickListener {
            unlikeService.menteeUnlike(myClass.user_id,intent.getStringExtra("mentor_id").toString())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ){
                        Log.d("결과:", "성공")
                    }
                    override fun onFailure(call: Call<ResponseBody  >, t: Throwable) {
                        Log.d("결과:", "실패 : $t")
                    }
                })
        }


        val myPageImg = findViewById<ImageView>(R.id.myPageImg)
        Glide.with(myPageImg)
            .load(intent.getStringExtra("mentor_profile"))
            .into(myPageImg)

        val myPageName = findViewById<TextView>(R.id.myPageName)
        myPageName.text = intent.getStringExtra("mentor_name")

        val myPageCompany = findViewById<TextView>(R.id.myPageCompany)
        myPageCompany.text = intent.getStringExtra("mentor_company")

        val myPageSchool = findViewById<TextView>(R.id.myPageSchool)
        myPageSchool.text = intent.getStringExtra("mentor_school")

        val myPageSubject = findViewById<TextView>(R.id.myPageSubject)
        myPageSubject.text = intent.getStringExtra("mentor_subject")

        val myPageGrade = findViewById<TextView>(R.id.myPageGrade)
        myPageGrade.text = intent.getStringExtra("mentor_grade")

        val myPageShort = findViewById<TextView>(R.id.shortIntroArea)
        myPageShort.text = intent.getStringExtra("mentor_short")

        val myPageLong = findViewById<TextView>(R.id.longIntroArea)
        myPageLong.text = intent.getStringExtra("mentor_long")
    }
}