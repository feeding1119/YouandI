package com.example.youandi.mypage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.youandi.R
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
import java.nio.file.Path

class TestActivity : AppCompatActivity() {

    lateinit var  profileImage : ImageView
    lateinit var  imgUri : Uri
    lateinit var  path : Path

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        findViewById<EditText>(R.id.myPagePwd)?.setText(myClass.user_pwd)
        findViewById<EditText>(R.id.myPageName)?.setText(myClass.user_name)
        findViewById<EditText>(R.id.myPageSchool)?.setText(myClass.user_school)
        findViewById<EditText>(R.id.myPageSubject)?.setText(myClass.user_subject)
        findViewById<EditText>(R.id.myPageGrade)?.setText(myClass.user_grade.toString())

        //uri를 절대 경로로 바꾸기
        fun createCopyAndReturnRealPath(uri: Uri): String? {
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
                while (inputStream.read(buf).also { len = it } > 0) outputStream.write(
                    buf,
                    0,
                    len
                )
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file.getAbsolutePath()
        }

        val okbtn = findViewById<Button>(R.id.btn)
        okbtn.setOnClickListener {
            val pwdText = findViewById<EditText>(R.id.pwdArea)?.text.toString()
            val nameText = findViewById<EditText>(R.id.nameArea)?.text.toString()
            val schoolText = findViewById<EditText>(R.id.schoolArea)?.text.toString()
            val subjectText = findViewById<EditText>(R.id.subjectArea)?.text.toString()
            val gradeText = findViewById<EditText>(R.id.gradeArea)?.text.toString()

            //The gson builder

            myClass.user_pwd = pwdText
            myClass.user_name = nameText
            myClass.user_school = schoolText
            myClass.user_subject = subjectText
            myClass.user_grade = gradeText.toFloat()

            var gson: Gson = GsonBuilder()
                .setLenient()
                .create()

            //creating retrofit object
            var retrofit =
                Retrofit.Builder()
                    .baseUrl("http://34.125.157.72:8080/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            //creating our api

            var server = retrofit.create(introEditInterface::class.java)

            val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_id)
            val userPwdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_pwd)
            val userNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_name)
            val userSchoolRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_school)
            val userClassRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_subject)
            val userCompanyRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_company)
            val userShortIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),myClass.shortIntro)
            val userLongIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),myClass.longIntro)
            val userGradeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), myClass.user_grade.toString())

            val path = createCopyAndReturnRealPath(imgUri)

            val file = File(path)
            var fileName = myClass.user_id.replace("@","").replace(".","")
            fileName = fileName+".png"

            var requestBody1 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
            var body1 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadProfile",fileName,requestBody1)

            var requestBody2 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
            var body2 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadGraduationFile",fileName,requestBody2)

            var requestBody3 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
            var body3 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadCompanyFile",fileName,requestBody3)

            server.modify(userIdRequestBody,userPwdRequestBody,userNameRequestBody,userSchoolRequestBody,userGradeRequestBody,userClassRequestBody,userCompanyRequestBody,userShortIntro,userLongIntro)	// body 가 Multipart.Part
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("error : ", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        Log.d("tag : ","good")

                    }
                })

        }


            //이미지 불러오기
            profileImage = findViewById(R.id.imageArea)

            val getAction = registerForActivityResult(
                ActivityResultContracts.GetContent(),
                ActivityResultCallback { uri ->
                    profileImage.setImageURI(uri)
                    imgUri = uri
                }
            )

            profileImage.setOnClickListener {
                getAction.launch("image/*")
            }

    }
}