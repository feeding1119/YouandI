package com.example.youandi.auth

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.youandi.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_mentee_join.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MenteeJoinActivity : AppCompatActivity() {

    lateinit var  profileImage : ImageView
    var imgUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentee_join)

        val spinnerschool = findViewById<Spinner>(R.id.spinnerId)
        spinnerschool.adapter = ArrayAdapter.createFromResource(this,R.array.schoolList,android.R.layout.simple_spinner_item)

        val spinnersubject = findViewById<Spinner>(R.id.spinnerId2)
        spinnersubject.adapter = ArrayAdapter.createFromResource(this,R.array.subjectList,android.R.layout.simple_spinner_item)

        fun menteeServer(path : String){

            val userId = findViewById<TextInputEditText>(R.id.idArea).text.toString()
            val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userId )
            val userPwd = findViewById<TextInputEditText>(R.id.pwdArea).text.toString()
            val userPwdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userPwd )
            val userName = findViewById<TextInputEditText>(R.id.nameArea).text.toString()
            val userNameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userName )
            val userSchool = findViewById<Spinner>(R.id.spinnerId).selectedItem.toString()
            val userSchoolRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userSchool )
            val userClass = findViewById<Spinner>(R.id.spinnerId2).selectedItem.toString()
            val userClassRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userClass )
            val userGrade = findViewById<TextInputEditText>(R.id.gradeArea).text.toString()
            val userGradeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userGrade )
            //val userGradeRequestBody = RequestBody.create(MediaType.parse("text/plain"),userGrade )

            if(userId==""||userName==""||userPwd==""||userGrade==""){
                Toast.makeText(this, "입력되지 않은 회원정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }else{
                val userPwdCheck = findViewById<TextInputEditText>(R.id.pwdCheckArea)

                if(userPwd!=userPwdCheck.text.toString()){
                    Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    //creating a file
                    val file = File(path)
                    var fileName = userId.replace("@","").replace(".","")
                    fileName = fileName+".png"

                    var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
                    var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploadProfile",fileName,requestBody)

                    //The gson builder
                    var gson : Gson =  GsonBuilder()
                        .setLenient()
                        .create()

                    //creating retrofit object
                    var retrofit =
                        Retrofit.Builder()
                            .baseUrl("http://34.125.157.72:8080/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()


                    //creating our api

                    var server = retrofit.create(retrofit_interface::class.java)


                    // 파일, 사용자 아이디, 파일이름

                    server.post_Porfile_Request(userIdRequestBody,userPwdRequestBody,userNameRequestBody,userSchoolRequestBody,userGradeRequestBody,userClassRequestBody,body)	// body 가 Multipart.Part
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

                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                }

            }

        }


        //이미지 불러오기
        profileImage=findViewById(R.id.imageArea)

        val getAction = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                profileImage.setImageURI(uri)
                imgUri=uri
            }
        )

        profileImage.setOnClickListener{
            getAction.launch("image/*")
        }



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


        val joinBtn=findViewById<Button>(R.id.menteeJoinBtn)
        joinBtn.setOnClickListener{

            if(imgUri==null){
                Toast.makeText(this, "프로필 이미지가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }else{
                val path = createCopyAndReturnRealPath(imgUri!!)

                menteeServer(path.toString())

            }
        }
    }
}
