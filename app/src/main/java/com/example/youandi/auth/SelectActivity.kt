package com.example.youandi.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.youandi.R

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val mentorBtn = findViewById<Button>(R.id.mentorBtn)
        mentorBtn.setOnClickListener {

            val intent = Intent(this, MentorJoinActivity::class.java)
            startActivity(intent)
        }

        val menteeBtn = findViewById<Button>(R.id.menteeBtn)
        menteeBtn.setOnClickListener {

            val intent = Intent(this, MenteeJoinActivity::class.java)
            startActivity(intent)
        }

    }
}