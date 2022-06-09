package com.example.youandi.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.youandi.R

class mGuide1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mguide1)

        val loginBtn = findViewById<Button>(R.id.nextBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, mGuide2Activity::class.java)

            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}