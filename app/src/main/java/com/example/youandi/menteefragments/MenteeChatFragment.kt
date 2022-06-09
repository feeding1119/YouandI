package com.example.youandi.menteefragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.youandi.AllListViewAdapter
import com.example.youandi.ListViewAdapter
import com.example.youandi.R
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.menteechat.MenteeChatActivity
import com.example.youandi.databinding.FragmentMenteeChatBinding
import com.example.youandi.menteechat.MenteeChatAdapter
import com.example.youandi.mypage.myClass
import com.example.youandi.serverdata.Mentor
import com.example.youandi.serverdata.serverDataInterface2
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
 * Use the [MenteeChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenteeChatFragment : Fragment() {

    private val mentorDataList= mutableListOf<MentorLoginData>()

    private lateinit var binding : FragmentMenteeChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentee_chat, container, false)

        val allRVAdapter =MenteeChatAdapter(mentorDataList)
        binding.allListView.adapter = allRVAdapter

        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MenteeLikeListInterface::class.java)


        service.getMentor(myClass.user_id).enqueue(object : Callback<List<MentorLoginData>> {
            override fun onResponse(
                call: Call<List<MentorLoginData>>,
                response: Response<List<MentorLoginData>>
            ) {
                val result : List<MentorLoginData>? =  response.body()

                if(result?.size!=0){
                    for (Mentor in result!!) {
                        mentorDataList.add(Mentor)
                    }
                }else if(result?.size==0){
                    binding.okid.text = "좋아요한 멘토가 없어요"
                }

                allRVAdapter.notifyDataSetChanged()
                Log.d("결과", mentorDataList.toString())

            }

            override fun onFailure(call: Call<List<MentorLoginData>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })


        binding.allListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(context, MenteeChatActivity::class.java)

            intent.putExtra("mentor_id",mentorDataList[i].user_id.toString())
            intent.putExtra("mentor_name",mentorDataList[i].user_name.toString())

            myClass.other_index=mentorDataList[i].user_index
            myClass.other_profile=mentorDataList[i].user_profile

            startActivity(intent)
        }
        binding.recommendTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeChatFragment_to_mentorRecommandFragment)
        }
        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeChatFragment_to_mentorAllFragment)
        }
        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeChatFragment_to_menteeMypageFragment)
        }
        return binding.root
    }

}