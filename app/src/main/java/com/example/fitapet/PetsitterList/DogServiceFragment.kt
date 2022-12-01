package com.example.fitapet.PetsitterList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.PetsitterList.TogetherServiceDetail.ChooseFriendAdapter
import com.example.fitapet.PetsitterList.TogetherServiceDetail.TogetherServiceFragment
import com.example.fitapet.Cookie
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentDogServiceBinding
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.Petsitter
import com.example.fitapet.retrofit.dto.searchPsitter
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogServiceFragment : Fragment() {
    private var _binding: FragmentDogServiceBinding? = null
    private val binding get() = _binding!!
    var map1 = HashMap<String, String>()
    var petsittercards= mutableListOf<PetsitterCard>()
    val petsitterListAdapter=PetsitterListAdapter(petsittercards)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogServiceBinding.inflate(inflater,container,false)
//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)

        //map1.put("sex", "F")
        //map1.put("isWalkable_YN", "Y")
        Log.d("iamhere", Cookie.userId.toString())
        var wp = 0
        binding.wPetsitter.setOnClickListener {
            if (wp==0){
                binding.wPetsitter.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("sex", "F")
                wp=1
            }else{
                binding.wPetsitter.setBackgroundResource(R.drawable.friend_button)
                map1.remove("sex")
                wp=0
            }
            apicon()
            //Log.d("apicon", map1.size.toString())
        }
        var mp = 0
        binding.mPetsitter.setOnClickListener {
            if (mp==0){
                binding.mPetsitter.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("sex", "M")
                mp=1
            }else{
                binding.mPetsitter.setBackgroundResource(R.drawable.friend_button)
                map1.remove("sex")
                mp=0
            }
            apicon()
        }
        var cam = 0
        binding.cameraAgree.setOnClickListener {
            if (cam==0){
                binding.cameraAgree.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("isAgreeToFilm_YN", "Y")
                cam=1
            }else{
                binding.cameraAgree.setBackgroundResource(R.drawable.friend_button)
                map1.remove("isAgreeToFilm_YN")
                cam=0
            }
            apicon()
        }
        var loc = 0
        binding.locationAgree.setOnClickListener {
            if (loc==0){
                binding.locationAgree.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("isAgreeSharingLocation_YN", "Y")
                loc=1
            }else{
                binding.locationAgree.setBackgroundResource(R.drawable.friend_button)
                map1.remove("isAgreeSharingLocation_YN")
                loc=0
            }
            apicon()
        }
        var jog = 0
        binding.joggingAvailable.setOnClickListener {
            if (jog==0){
                binding.joggingAvailable.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("isWalkable_YN", "Y")
                jog=1
            }else{
                binding.joggingAvailable.setBackgroundResource(R.drawable.friend_button)
                map1.remove("isWalkable_YN")
                jog=0
            }
            apicon()
        }
        var old = 0
        binding.oldDogOk.setOnClickListener {
            if (old==0){
                binding.oldDogOk.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("isPossibleCareOldPet_YN", "Y")
                old=1
            }else{
                binding.oldDogOk.setBackgroundResource(R.drawable.friend_button)
                map1.remove("isPossibleCareOldPet_YN")
                old=0
            }
            apicon()
        }
        var own = 0
        binding.havePet.setOnClickListener {
            if (own==0){
                binding.havePet.setBackgroundResource(R.drawable.blue_side_btn)
                map1.put("hasPet_YN", "Y") //Query Key값 확인
                own=1
            }else{
                binding.havePet.setBackgroundResource(R.drawable.friend_button)
                map1.remove("hasPet_YN")
                own=0
            }
            apicon()
        }

        apicon() //기본(초기)펫시터 리스트 얻어오기

        //petsittercards.add(PetsitterCard("R.drawable.example1","김도선","10년이상","반려동물 있음","남","25","안녕하세요"))
        //petsittercards.add(PetsitterCard("R.drawable.example1","정민욱","10년이상","반려동물 있음","남","25","안녕하세요"))

        binding.recylcerView.layoutManager=LinearLayoutManager(requireContext())
        //binding.recylcerView.adapter=petsitterListAdapter
        petsitterListAdapter.setItemClickListener(object : PetsitterListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                loadFragment(PetListRecyclerFragment())
            }

        })

        binding.visit.setOnClickListener {
            //loadFragment(TogetherServiceFragment())
        }

        binding.togo.setOnClickListener {
            //loadFragment(TogetherServiceFragment())
        }

        return binding.root
    }

    fun apicon(){
        RetrofitClient.apiServer.searchPsitter(4, map1).enqueue(object: Callback<searchPsitter>{
            override fun onResponse(call: Call<searchPsitter>, response: Response<searchPsitter>) {
                petsittercards.clear()
                Log.d("iamhere", "여기있다")
                //petsittercards = response.body()!!.result as MutableList<Petsitter>
                val responseResult=response.body()!!.result
                Log.d("iamhere4", responseResult.size.toString())
                for (petsitter in responseResult){
                    Log.d("iamhere2", "여기있다2")
                    petsittercards.add(PetsitterCard(petsitter.petSitterProfileImg, petsitter.petSitterName, petsitter.career,
                        petsitter.hasPet_YN, petsitter.sex, petsitter.age.toString(), petsitter.selfIntroduction, petsitter.isAgreeToFilm_YN,
                        petsitter.isAgreeSharingLocation_YN, petsitter.isWalkable_YN, petsitter.isPossibleCareOldPet_YN, petsitter.isBookMark))
                }

                Log.d("iamhere3", petsittercards.size.toString())
                binding.recylcerView.adapter=petsitterListAdapter
            }

            override fun onFailure(call: Call<searchPsitter>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}