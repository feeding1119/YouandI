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
import com.example.youandi.R
import com.example.youandi.auth.IntroActivity
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.auth.MentorLoginService
import com.example.youandi.databinding.FragmentMentorChatBinding
import com.example.youandi.databinding.FragmentMentorMypageBinding
import com.example.youandi.mypage.MenteeMyPageActivity
import com.example.youandi.mypage.MentorMyPageActivity
import com.example.youandi.mypage.myClass
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
 * Use the [MentorMypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorMypageFragment : Fragment() {

    private lateinit var binding : FragmentMentorMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentor_mypage, container, false)

        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorMypageFragment_to_mentorChatFragment)
        }
        binding.likeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorMypageFragment_to_menteeLikeFragment)
        }

        binding.mypageBtn.setOnClickListener {
            val intent = Intent(context, MentorMyPageActivity::class.java)
            startActivity(intent)
        }


        binding.logoutBtn.setOnClickListener {
            val intent = Intent(context, IntroActivity::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}