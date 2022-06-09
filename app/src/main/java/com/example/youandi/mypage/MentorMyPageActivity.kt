package com.example.youandi.mypage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.MentorNaviMainActivity
import com.example.youandi.R
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.auth.MentorLoginService
import com.example.youandi.auth.retrofit_interface2
import com.example.youandi.guide.Guide2Activity
import com.google.android.material.textfield.TextInputEditText
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
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Math.E
import java.net.URL
import java.sql.Types.NULL

class MentorMyPageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_my_page)


        val myPageImg = findViewById<ImageView>(R.id.myPageImg)
        Glide.with(myPageImg)
            .load(myClass.user_profile)
            .into(myPageImg)

        val myPageName = findViewById<TextView>(R.id.myPageName)
        myPageName.text=myClass.user_name

        val myPageCompany = findViewById<TextView>(R.id.myPageCompany)
        myPageCompany.text=myClass.user_company

        val myPageSchool = findViewById<TextView>(R.id.myPageSchool)
        myPageSchool.text=myClass.user_school

        val myPageSubject = findViewById<TextView>(R.id.myPageSubject)
        myPageSubject.text=myClass.user_subject

        val myPageGrade = findViewById<TextView>(R.id.myPageGrade)
        myPageGrade.text=myClass.user_grade.toString()

        val myPageShort = findViewById<TextView>(R.id.shortIntroArea)
        myPageShort.text=myClass.shortIntro

        val myPageLong = findViewById<TextView>(R.id.longIntroArea)
        myPageLong.text=myClass.longIntro


        //uri를 절대 경로로 바꾸기
        fun createCopyAndReturnRealPath(uri: Uri) :String? {
            val context = applicationContext
            val contentResolver = context.contentResolver ?: return null

            // Create file path inside app's data dir
            val filePath = (context.applicationInfo.dataDir + File.separator
                    + System.currentTimeMillis())
            val file = File(filePath)
            try {
                val inputStream = contentResolver.openInputStream(uri) ?: return null
                val outputStream: OutputStream = FileOutputStream(file)
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file.getAbsolutePath()
        }

        val edit2Btn = findViewById<Button>(R.id.btn1)
        edit2Btn.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
            }

        val edit3Btn = findViewById<Button>(R.id.btn3)
        edit3Btn.setOnClickListener {
            val intent = Intent(this, CompanyEditActivity::class.java)
            startActivity(intent)
        }

        val edit4Btn = findViewById<Button>(R.id.btn4)
        edit4Btn.setOnClickListener {
            val intent = Intent(this, GraduationEditActivity::class.java)
            startActivity(intent)
        }
        val edit1Btn = findViewById<Button>(R.id.btn2)
        edit1Btn.setOnClickListener {
            val intent = Intent(this, MentorEdit1Activity::class.java)
            startActivity(intent)




//
//            mAlertDialog.findViewById<EditText>(R.id.shortIntroArea)?.setText(myClass.shortIntro)
//            mAlertDialog.findViewById<EditText>(R.id.longIntroArea)?.setText(myClass.longIntro)
//
//
//                val shortIntroText = mAlertDialog.findViewById<EditText>(R.id.shortIntroArea)?.text.toString()
//                val longIntroText = mAlertDialog.findViewById<EditText>(R.id.longIntroArea)?.text.toString()
//                //The gson builder
//
//                myClass.shortIntro = shortIntroText
//                myClass.longIntro = longIntroText
//                var gson : Gson =  GsonBuilder()
//                    .setLenient()
//                    .create()
//
//                //creating retrofit object
//                var retrofit =
//                    Retrofit.Builder()
//                        .baseUrl("http://34.125.157.72:8080/")
//                        .addConverterFactory(ScalarsConverterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .build()
//                //creating our api
//
//                var server = retrofit.create(introEditInterface::class.java)
//
//                val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_id )
//                val userPwdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_pwd)
//                val userNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_name )
//                val userSchoolRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_school )
//                val userClassRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_subject )
//                val userGradeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_grade.toString() )
//                val userWorkRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_company)
//                val userShortIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),shortIntroText)
//                val userLongIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),longIntroText)
//
//                //
//                server.modify(userIdRequestBody,userPwdRequestBody,userNameRequestBody,userSchoolRequestBody,userGradeRequestBody,userClassRequestBody,userWorkRequestBody ,userShortIntro,userLongIntro)	// body 가 Multipart.Part
//                    .enqueue(object : Callback<String> {
//                        override fun onFailure(call: Call<String>, t: Throwable) {
//                            Log.e("error : ", t.message.toString())
//                        }
//
//                        override fun onResponse(
//                            call: Call<String>,
//                            response: Response<String>
//                        ) {
//                            Log.d("tag : ","good")
//
//                        }
//                    })
//                mAlertDialog.dismiss()
//                finish()
//                startActivity(intent)
//
//            }
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MentorNaviMainActivity::class.java)
        startActivity(intent)

    }

}