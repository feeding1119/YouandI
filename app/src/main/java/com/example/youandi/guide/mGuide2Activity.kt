package com.example.youandi.guide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.youandi.MentorNaviMainActivity
import com.example.youandi.R
import com.example.youandi.serverdata.Mentor

class mGuide2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mguide2)

        val loginBtn = findViewById<Button>(R.id.nextBtn)
        loginBtn.setOnClickListener {
            val intent = Intent(this, MentorNaviMainActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}
