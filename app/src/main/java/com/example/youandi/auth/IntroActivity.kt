package com.example.youandi.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.example.youandi.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val menteeLoginBtn = findViewById<Button>(R.id.menteeLoginBtn)
        menteeLoginBtn.setOnClickListener {

            val intent = Intent(this, MenteeLoginActivity::class.java)
            startActivity(intent)
        }

        val mentorLoginBtn = findViewById<Button>(R.id.mentorLoginBtn)
        mentorLoginBtn.setOnClickListener {

            val intent = Intent(this, MentorLoginActivity::class.java)
            startActivity(intent)
        }

        val joinBtn = findViewById<Button>(R.id.joinBtn)
        joinBtn.setOnClickListener {

            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }
    }
}