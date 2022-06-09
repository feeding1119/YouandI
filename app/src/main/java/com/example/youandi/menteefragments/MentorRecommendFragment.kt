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
import com.example.youandi.CardStackAdapter
import com.example.youandi.MenteeNaviMainActivity
import com.example.youandi.R
import com.example.youandi.auth.MentorLoginData
import com.example.youandi.databinding.FragmentMentorAllBinding
import com.example.youandi.databinding.FragmentMentorRecommendBinding
import com.example.youandi.mypage.myClass
import com.example.youandi.serverdata.Mentor
import com.example.youandi.serverdata.serverDataInterface2
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_mentor_recommend.*
import okhttp3.ResponseBody
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
 * Use the [MentorRecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorRecommandFragment : Fragment() {

    private val mentorDataList= mutableListOf<MentorLoginData>()

    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager: CardStackLayoutManager


    private lateinit var binding : FragmentMentorRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mentor_recommend, container, false)


        //서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.125.157.72:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MentorMatchingInterface::class.java)
        val likeService = retrofit.create(MenteeLikeInterface::class.java)
        val unlikeService = retrofit.create(MenteeUnlikeInterface::class.java)


        service.getMentor(myClass.user_id,myClass.user_school,myClass.user_grade,myClass.user_subject).enqueue(object : Callback<List<MentorLoginData>> {
            override fun onResponse(
                call: Call<List<MentorLoginData>>,
                response: Response<List<MentorLoginData>>
            ) {
                val result : List<MentorLoginData>? =  response.body()
                for (Mentor in result!!) {
                    mentorDataList.add(Mentor)
                }
                cardStackAdapter.notifyDataSetChanged()
                Log.d("결과", mentorDataList.toString())

            }

            override fun onFailure(call: Call<List<MentorLoginData>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })

        //카드 스택뷰
        var user_count = 0

        manager = CardStackLayoutManager(activity,object :CardStackListener{

            override fun onCardDragging(direction: Direction?, ratio: Float) {


            }

            override fun onCardSwiped(direction: Direction?) {

                if(direction==Direction.Left){
                    unlikeService.menteeUnlike(myClass.user_id,mentorDataList[user_count].user_id)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ){
                                Log.d("결과:", "성공")
                            }
                            override fun onFailure(call: Call<ResponseBody  >, t: Throwable) {
                                Log.d("결과:", "실패 : $t")
                            }
                        })
                    user_count += 1


                }

                if(direction==Direction.Right){
                    likeService.menteeLike(myClass.user_id,mentorDataList[user_count].user_id)
                        .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ){
                            Log.d("결과:", "성공")
                        }
                        override fun onFailure(call: Call<ResponseBody  >, t: Throwable) {
                            Log.d("결과:", "실패 : $t")
                        }
                    })
                    user_count += 1
                }

                if(user_count==mentorDataList.size){
                    binding.okid.setText("추천 멘토를 모두 확인하셨습니다.")
                    binding.refreshbtn.visibility=View.VISIBLE

                    binding.refreshbtn.setOnClickListener {
                        val intent = Intent(activity, MenteeNaviMainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }


            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })


        manager.setStackFrom(StackFrom.Right)
        manager.setTranslationInterval(8.0f)

        cardStackAdapter = CardStackAdapter(activity!!, mentorDataList)
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = cardStackAdapter


        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorRecommandFragment_to_mentorAllFragment)
        }
        binding.mypageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorRecommandFragment_to_menteeMypageFragment)
        }
        binding.chatTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mentorRecommandFragment_to_menteeChatFragment)
        }
        return binding.root
    }



}