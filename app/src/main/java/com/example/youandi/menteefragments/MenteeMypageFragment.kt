package com.example.youandi.menteefragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.youandi.R
import com.example.youandi.auth.IntroActivity
import com.example.youandi.auth.MenteeLoginData
import com.example.youandi.auth.MenteeLoginService
import com.example.youandi.databinding.FragmentMenteeMypageBinding
import com.example.youandi.guide.Guide1Activity
import com.example.youandi.mypage.MenteeLikePageActivity
import com.example.youandi.mypage.MenteeMyPageActivity
import com.example.youandi.mypage.myClass
import com.google.android.material.textfield.TextInputEditText
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
 * Use the [MenteeMypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenteeMypageFragment : Fragment() {

    private lateinit var binding : FragmentMenteeMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment



        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentee_mypage, container, false)

        binding.recommendTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeMypageFragment_to_mentorRecommandFragment)
        }
        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeMypageFragment_to_mentorAllFragment)
        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_menteeMypageFragment_to_menteeChatFragment)
        }

        binding.mypageBtn.setOnClickListener {
            val intent = Intent(context, MenteeMyPageActivity::class.java)
            startActivity(intent)
        }

        binding.likeListBtn.setOnClickListener {
            val intent = Intent(context, MenteeLikePageActivity::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(context, IntroActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}