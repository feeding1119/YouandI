package com.example.youandi.mentorfragments

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
import com.example.youandi.databinding.FragmentMenteeAllBinding
import com.example.youandi.databinding.FragmentMentorAllBinding
import com.example.youandi.serverdata.Mentee
import com.example.youandi.serverdata.Mentor
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
 * Use the [MenteeAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenteeAllFragment : Fragment() {

    private lateinit var binding : FragmentMenteeAllBinding

    private val menteeDataList= mutableListOf<Mentee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentee_all, container, false)

        val allRVAdapter = ListViewAdapter(menteeDataList)

        binding.allListView.adapter = allRVAdapter

        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(serverDataInterface::class.java)


        service.getMentee().enqueue(object : Callback<List<Mentee>> {
            override fun onResponse(
                call: Call<List<Mentee>>,
                response: Response<List<Mentee>>
            ) {
                val result : List<Mentee>? =  response.body()
                for (Mentee in result!!) {
                    menteeDataList.add(Mentee)
                }
                allRVAdapter.notifyDataSetChanged()
                Log.d("결과", menteeDataList.toString())

            }

            override fun onFailure(call: Call<List<Mentee>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })


//
//        binding.likeTap.setOnClickListener {
//            it.findNavController().navigate(R.id.action_menteeAllFragment_to_menteeLikeFragment)
//        }
//        binding.chatTap.setOnClickListener {
//            it.findNavController().navigate(R.id.action_menteeAllFragment_to_mentorChatFragment)
//        }
//        binding.mypageTap.setOnClickListener {
//            it.findNavController().navigate(R.id.action_menteeAllFragment_to_mentorMypageFragment)
//        }
        // Inflate the layout for this fragment
        return binding.root
    }

}