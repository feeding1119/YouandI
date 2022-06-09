package com.example.youandi.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.youandi.R
import com.example.youandi.guide.Guide2Activity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MentorEdit1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_edit1)

        findViewById<EditText>(R.id.shortIntroArea)?.setText(myClass.shortIntro)
        findViewById<EditText>(R.id.longIntroArea)?.setText(myClass.longIntro)


        val editBtn = findViewById<Button>(R.id.editBtn)
        editBtn.setOnClickListener {

            val shortIntroText = findViewById<EditText>(R.id.shortIntroArea)?.text.toString()
            val longIntroText = findViewById<EditText>(R.id.longIntroArea)?.text.toString()

            myClass.shortIntro = shortIntroText
            myClass.longIntro = longIntroText

            var gson : Gson =  GsonBuilder()
                .setLenient()
                .create()

            //creating retrofit object
            var retrofit =
                Retrofit.Builder()
                    .baseUrl("http://34.125.157.72:8080/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            //creating our api

            var server = retrofit.create(introEditInterface::class.java)

            val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_id )
            val userPwdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_pwd)
            val userNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_name )
            val userSchoolRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_school )
            val userClassRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_subject )
            val userGradeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_grade.toString() )
            val userWorkRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_company)
            val userShortIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),shortIntroText)
            val userLongIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),longIntroText)

            //
            server.modify(userIdRequestBody,userPwdRequestBody,userNameRequestBody,userSchoolRequestBody,userGradeRequestBody,userClassRequestBody,userWorkRequestBody ,userShortIntro,userLongIntro)	// body 가 Multipart.Part
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("error : ", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        Log.d("tag : ","good")

                    }
                })

            val intent = Intent(this, MentorMyPageActivity::class.java)
            startActivity(intent)
//
        }
    }
}