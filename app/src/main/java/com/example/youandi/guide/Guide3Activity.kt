package com.example.youandi.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.R

class Guide3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide3)

        val loginBtn = findViewById<Button>(R.id.endBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, MenteeNaviMainActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}