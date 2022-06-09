package com.example.youandi.menteefragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.youandi.AllListViewAdapter
import com.example.youandi.CardStackAdapter
import com.example.youandi.R
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.databinding.FragmentMentorAllBinding
import com.example.youandi.menteechat.MenteeChatActivity
import com.example.youandi.mypage.MentorDataActivity
import com.example.youandi.mypage.myClass
import com.example.youandi.serverdata.Mentor
import com.example.youandi.serverdata.serverDataInterface2
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorAllFragment : Fragment() {

    private val mentorDataList= mutableListOf<MentorLoginData>()

    private lateinit var binding : FragmentMentorAllBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentor_all, container, false)

        val allRVAdapter = AllListViewAdapter(mentorDataList)
        binding.allListView.adapter = allRVAdapter

        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(serverDataInterface2::class.java)


        service.getMentor().enqueue(object : Callback<List<MentorLoginData>> {
            override fun onResponse(
                call: Call<List<MentorLoginData>>,
                response: Response<List<MentorLoginData>>
            ) {
                val result : List<MentorLoginData>? =  response.body()
                for (Mentor in result!!) {
                    mentorDataList.add(Mentor)
                }
                allRVAdapter.notifyDataSetChanged()
                Log.d("결과", mentorDataList.toString())

            }

            override fun onFailure(call: Call<List<MentorLoginData>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })

        binding.allListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(context, MentorDataActivity::class.java)

            intent.putExtra("mentor_id",mentorDataList[i].user_id.toString())
            intent.putExtra("mentor_name",mentorDataList[i].user_name.toString())
            intent.putExtra("mentor_company",mentorDataList[i].user_company.toString())
            intent.putExtra("mentor_school",mentorDataList[i].user_school.toString())
            intent.putExtra("mentor_subject",mentorDataList[i].user_subject.toString())
            intent.putExtra("mentor_grade",mentorDataList[i].user_grade.toString())
            intent.putExtra("mentor_short",mentorDataList[i].user_short)
            intent.putExtra("mentor_long",mentorDataList[i].user_long)
            intent.putExtra("mentor_profile",mentorDataList[i].user_profile)

            startActivity(intent)
        }

        binding.searchbtn.setOnClickListener {

            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.serach_dialog,null)
            val mBuilder = AlertDialog.Builder(activity!!)
                .setView(mDialogView)
                .setTitle("상세 검색")

            val mAlertDialog = mBuilder.show()

            val spinnerschool = mAlertDialog.findViewById<Spinner>(R.id.spinnerId)
            spinnerschool!!.adapter = ArrayAdapter.createFromResource(activity!!,R.array.schoolList,android.R.layout.simple_spinner_item)

            val spinnersubject = mAlertDialog.findViewById<Spinner>(R.id.spinnerId2)
            spinnersubject!!.adapter = ArrayAdapter.createFromResource(activity!!,R.array.subjectList,android.R.layout.simple_spinner_item)

            val gradetext = mAlertDialog.findViewById<TextInputEditText>(R.id.gradeArea)

            val btn = mAlertDialog.findViewById<Button>(R.id.editBtn)

            btn?.setOnClickListener {

                if(gradetext.toString()==""){
                    Toast.makeText(activity,"성적을 입력하세요",Toast.LENGTH_SHORT).show()
                }else{
                    //서버 연결
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://34.125.157.72:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val service = retrofit.create(MentorMatchingInterface::class.java)

                    service.getMentor(myClass.user_id,spinnerschool.selectedItem.toString(),gradetext?.text.toString().toFloat(),spinnersubject.selectedItem.toString()).enqueue(object : Callback<List<MentorLoginData>> {
                        override fun onResponse(
                            call: Call<List<MentorLoginData>>,
                            response: Response<List<MentorLoginData>>
                        ) {
                            val result : List<MentorLoginData>? =  response.body()
                            mentorDataList.clear()
                            if(result?.size!=0){

                                for (Mentor in result!!) {
                                    mentorDataList.add(Mentor)
                                }

                                Log.d("결과", mentorDataList.toString())

                            }else if(result?.size==0){

                                binding.okid.text = "검색된 멘토가 없어요"
                            }
                            allRVAdapter.notifyDataSetChanged()

                        }

                        override fun onFailure(call: Call<List<MentorLoginData>>, t: Throwable) {
                            Log.d("결과:", "실패 : $t")
                        }
                    })

                    mAlertDialog.dismiss()
                }

            }
        }



        binding.recommendTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorAllFragment_to_mentorRecommandFragment2)
        }
        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorAllFragment_to_menteeMypageFragment2)
        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorAllFragment_to_menteeChatFragment)
        }
        return binding.root
    }

}