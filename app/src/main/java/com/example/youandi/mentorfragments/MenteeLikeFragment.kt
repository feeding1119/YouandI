package com.example.youandi.mentorfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.youandi.ListViewAdapter
import com.example.youandi.R
import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.databinding.FragmentMenteeAllBinding
import com.example.youandi.databinding.FragmentMenteeLikeBinding
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
 * Use the [MenteeLikeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenteeLikeFragment : Fragment() {

    private lateinit var binding : FragmentMenteeLikeBinding

    private val menteeDataList= mutableListOf<MenteeLoginData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentee_like, container, false)

        val allRVAdapter = MenteeListViewAdapter(menteeDataList)

        binding.likeListView.adapter = allRVAdapter

        //서버 연결
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
                    binding.okid.text = "좋아요한 멘티가 없어요"
                }
                allRVAdapter.notifyDataSetChanged()
                Log.d("결과", menteeDataList.toString())

            }

            override fun onFailure(call: Call<List<MenteeLoginData>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })


        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeLikeFragment_to_mentorChatFragment)
        }
        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeLikeFragment_to_mentorMypageFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}