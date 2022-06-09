package com.example.youandi.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.youandi.R
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MentorJoinActivity : AppCompatActivity() {

    var fileUri3 : Uri? = null
    var fileUri1 : Uri? = null
    var fileUri2 : Uri? = null

    lateinit var  profileImage : ImageView
    lateinit var  file1Image : ImageView
    lateinit var  file2Image : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_join)

        val spinnerschool = findViewById<Spinner>(R.id.spinnerId)
        spinnerschool.adapter = ArrayAdapter.createFromResource(this,R.array.schoolList,android.R.layout.simple_spinner_item)

        val spinnersubject = findViewById<Spinner>(R.id.spinnerId2)
        spinnersubject.adapter = ArrayAdapter.createFromResource(this,R.array.subjectList,android.R.layout.simple_spinner_item)

        fun testRetrofit(path1 : String,path2 : String,path3 : String){

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
            val userWork = findViewById<TextInputEditText>(R.id.workArea).text.toString()
            val userWorkRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userWork )
            val userShortIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),"안녕하십니까 $userName 멘토입니다.")
            val userLongIntro = RequestBody.create("text/pain".toMediaTypeOrNull(),"안녕하십니까 $userName 멘토입니다.")

            if(userId==""||userName==""||userPwd==""||userGrade==""||userWork==""){
                Toast.makeText(this, "입력되지 않은 회원정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }else{

                val userPwdCheck = findViewById<TextInputEditText>(R.id.pwdCheckArea)

                if(userPwd!=userPwdCheck.text.toString()){
                    Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    //creating a file
                    val file1 = File(path1)
                    var fileName = userId.replace("@","").replace(".","")
                    fileName = fileName+".png"

                    var requestBody1 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file1)
                    var body1 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadProfile","test",requestBody1)

                    val file2 = File(path2)
//
                    var requestBody2 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file2)
                    var body2 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadGraduationFile","test",requestBody2)

                    val file3 = File(path3)
//

                    var requestBody3 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file3)
                    var body3 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadCompanyFile","test",requestBody3)

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

                    var server = retrofit.create(retrofit_interface2::class.java)


                    // 파일, 사용자 아이디, 파일이름

                    server.post_Porfile_Request(userIdRequestBody,userPwdRequestBody,userNameRequestBody,userSchoolRequestBody,userGradeRequestBody,userClassRequestBody,userWorkRequestBody ,userShortIntro,userLongIntro,body1,body2,body3)	// body 가 Multipart.Part
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
                    Toast.makeText(this, "승인 후 서비스 이용이 가능합니다.", Toast.LENGTH_LONG).show()
                }

            }

        }

        //프로필 이미지
        profileImage=findViewById(R.id.imageArea)

        val getAction1 = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                profileImage.setImageURI(uri)
                fileUri3=uri
            }
        )
        profileImage.setOnClickListener{
            getAction1.launch("image/*")
        }

        //재직증명서
        file1Image=findViewById(R.id.file1img)
        val getAction2 = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                file1Image.setImageURI(uri)
                fileUri1=uri
            }
        )
        file1Image.setOnClickListener{
            getAction2.launch("image/*")
        }

        //졸업증명서
        file2Image=findViewById(R.id.file2img)
        val getAction3 = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                file2Image.setImageURI(uri)
                fileUri2=uri
            }
        )
        file2Image.setOnClickListener{
            getAction3.launch("image/*")
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

        val joinBtn=findViewById<Button>(R.id.mentorJoinBtn)
        joinBtn.setOnClickListener{

            if(fileUri3 == null){
                Toast.makeText(this, "프로필 이미지가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }else if(fileUri1 ==null||fileUri2 == null){
                Toast.makeText(this, "증명서가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }else{
                val path1 = createCopyAndReturnRealPath(fileUri3!!)
                val path2 = createCopyAndReturnRealPath(fileUri1!!)
                val path3 = createCopyAndReturnRealPath(fileUri2!!)

                testRetrofit(path1.toString(),path2.toString(),path3.toString())

            }

        }
    }
}