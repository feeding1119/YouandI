package com.example.youandi.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.R
import com.example.youandi.guide.Guide1Activity
import com.example.youandi.mypage.myClass
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenteeLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var retrofit =
            Retrofit.Builder()
                .baseUrl("http://34.125.157.72:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        var loginService = retrofit.create(MenteeLoginService::class.java)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {

            val userId = findViewById<TextInputEditText>(R.id.idArea).text.toString()
            val userPwd = findViewById<TextInputEditText>(R.id.pwdArea).text.toString()

            loginService.loginRequest(userId,userPwd).enqueue(object : Callback<MenteeLoginData>{
                override fun onFailure(call : Call<MenteeLoginData>,t:Throwable){
                    Log.e("error : ", t.message.toString())
                    Toast.makeText(this@MenteeLoginActivity, "오류오류", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<MenteeLoginData>,response: Response<MenteeLoginData>){
                    var Login = response.body()

                    if (Login!!.user_status == true){

                        myClass.user_id=userId
                        myClass.user_pwd=userPwd
                        myClass.user_name=Login.user_name
                        myClass.user_school=Login.user_school
                        myClass.user_grade=Login.user_grade
                        myClass.user_subject=Login.user_subject
                        myClass.user_profile=Login.user_profile
                        myClass.user_index=Login.user_index

                        Toast.makeText(this@MenteeLoginActivity, "환영합니다. ${Login.user_name.toString()}님", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MenteeLoginActivity, Guide1Activity::class.java)

                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@MenteeLoginActivity, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
            })


        }
    }
}