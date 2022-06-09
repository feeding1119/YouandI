package com.example.youandi.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.youandi.R
import com.example.youandi.mypage.myClass

class Guide1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide1)

        val loginBtn = findViewById<Button>(R.id.nextBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, Guide2Activity::class.java)
            startActivity(intent)
//            overridePendingTransition(0,0)
//            Toast.makeText(this, myClass.user_pwd, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {

    }
}