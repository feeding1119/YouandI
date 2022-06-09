package com.example.youandi.mypage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.R
import com.example.youandi.menteefragments.MenteeMypageFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class MenteeMyPageActivity : AppCompatActivity() {

    lateinit var  profileImage : ImageView
    lateinit var  imgUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentee_my_page)

        val myPageImg = findViewById<ImageView>(R.id.myPageImg)
        Glide.with(myPageImg)
            .load(myClass.user_profile)
            .into(myPageImg)

        val myPageName = findViewById<TextView>(R.id.myPageName)
        myPageName.text=myClass.user_name

        val myPageSchool = findViewById<TextView>(R.id.myPageSchool)
        myPageSchool.text=myClass.user_school

        val myPageSubject = findViewById<TextView>(R.id.myPageSubject)
        myPageSubject.text=myClass.user_subject

        val myPageGrade = findViewById<TextView>(R.id.myPageGrade)
        myPageGrade.text=myClass.user_grade.toString()

        val testBtn = findViewById<Button>(R.id.btn)
        testBtn.setOnClickListener {
            val intent = Intent(this, mProfileEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenteeNaviMainActivity::class.java)
        startActivity(intent)

    }
}