package com.example.youandi.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.youandi.R

class Guide2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide2)

        val loginBtn = findViewById<Button>(R.id.nextBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, Guide3Activity::class.java)

            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}