package com.example.youandi.mentorfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.youandi.ListViewAdapter
import com.example.youandi.R
import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.databinding.FragmentMenteeLikeBinding
import com.example.youandi.databinding.FragmentMentorChatBinding
import com.example.youandi.menteechat.MenteeChatActivity
import com.example.youandi.mentorchat.AdminChatActivity
import com.example.youandi.mentorchat.MenChatActivity
import com.example.youandi.mentorchat.MentorChatActivity
import com.example.youandi.mentorchat.MentorChatAdapter
import com.example.youandi.mypage.myClass
import com.example.youandi.serverdata.Mentee
import com.example.youandi.serverdata.serverDataInterface
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
 * Use the [MentorChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorChatFragment : Fragment() {

    private lateinit var binding : FragmentMentorChatBinding

    private val menteeDataList= mutableListOf<MenteeLoginData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentor_chat, container, false)

        val allRVAdapter = MentorChatAdapter(menteeDataList)

        binding.likeListView.adapter = allRVAdapter

        //?????? ??????
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MentorLikeListInterface::class.java)


        service.getMentee(myClass.user_id).enqueue(object : Callback<List<MenteeLoginData>> {
            override fun onResponse(
                call: Call<List<MenteeLoginData>>,
                response: Response<List<MenteeLoginData>>
            ) {
                val result : List<MenteeLoginData>? =  response.body()
                if(result?.size!=0){
                    for (Mentee in result!!) {
                        menteeDataList.add(Mentee)
                    }
                }else if(result?.size==0){
                    binding.okid.text = "???????????? ????????? ?????????"
                }
                allRVAdapter.notifyDataSetChanged()
                Log.d("??????", menteeDataList.toString())

            }

            override fun onFailure(call: Call<List<MenteeLoginData>>, t: Throwable) {
                Log.d("??????:", "?????? : $t")
            }
        })

        binding.likeListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(context, MenChatActivity::class.java)

            intent.putExtra("mentee_id",menteeDataList[i].user_id.toString())
            intent.putExtra("mentee_name",menteeDataList[i].user_name.toString())

            myClass.other_profile=menteeDataList[i].user_profile
            myClass.other_index=menteeDataList[i].user_index
            startActivity(intent)
        }

        binding.adminchatBtn.setOnClickListener {
            val intent = Intent(context, AdminChatActivity::class.java)

            myClass.other_profile = "admin"
            myClass.other_index=1
            startActivity(intent)
        }

        binding.likeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorChatFragment_to_menteeLikeFragment)
        }
        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorChatFragment_to_mentorMypageFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}