package com.example.youandi.mypage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.youandi.MentorNaviMainActivity
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class CompanyEditActivity : AppCompatActivity() {

    lateinit var  profileImage : ImageView
    var imgUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_edit)

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

        val editBtn = findViewById<Button>(R.id.editBtn)
        editBtn.setOnClickListener {
            if(imgUri==null){
                Toast.makeText(this,"증명서 확인 필요", Toast.LENGTH_LONG).show()
            }else{
                val path = createCopyAndReturnRealPath(imgUri!!)
                val file = File(path)

                var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
                var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploadCompanyFile","test",requestBody)

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
                var server = retrofit.create(cFileInterface::class.java)

                val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),myClass.user_id )

                server.modify(userIdRequestBody,body)	// body 가 Multipart.Part
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

                val intent = Intent(this, MentorNaviMainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}