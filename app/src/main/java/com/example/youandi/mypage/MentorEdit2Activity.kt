package com.example.youandi.mypage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.youandi.R
import com.example.youandi.auth.retrofit_interface2
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

class MentorEdit2Activity : AppCompatActivity() {

    lateinit var  fileUri3: Uri
    lateinit var  fileUri1 : Uri
    lateinit var  fileUri2 : Uri

    lateinit var  profileImage : ImageView
    lateinit var  file1Image : ImageView
    lateinit var  file2Image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor_edit2)
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

        val editBtn = findViewById<Button>(R.id.editBtn)
        editBtn.setOnClickListener {

            val path1 = createCopyAndReturnRealPath(fileUri3)
            val file1 = File(path1)

            var requestBody1 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file1)
            var body1 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadProfile","test",requestBody1)

            val path2 = createCopyAndReturnRealPath(fileUri1)
            val file2 = File(path2)
            var requestBody2 : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file2)
            var body2 : MultipartBody.Part = MultipartBody.Part.createFormData("uploadGraduationFile","test",requestBody2)

            val path3 = createCopyAndReturnRealPath(fileUri2)
            val file3 = File(path3)
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
            var server = retrofit.create(fileEditInterface::class.java)

            val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_id )

            server.modify(userIdRequestBody,body1)	// body 가 Multipart.Part
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

            val intent = Intent(this, MentorMyPageActivity::class.java)
            startActivity(intent)
        }


    }
}