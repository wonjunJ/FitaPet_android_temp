package com.example.fitapet.ui.reservation.petList

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitapet.Cookie
import com.example.fitapet.MainActivity
import com.example.fitapet.PetsitterList.PetsitterListAdapter
import com.example.fitapet.PetsitterList.TogetherServiceDetail.ChooseFriendFragment
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentPetListRecyclerBinding
import com.example.fitapet.databinding.FragmentReservation01Binding
import com.example.fitapet.retrofit.RetrofitClient.apiServer
import com.example.fitapet.retrofit.dto.getPets
import com.example.fitapet.ui.reservation.Reservation01Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PetListRecyclerFragment : Fragment() {
    private var _binding: FragmentPetListRecyclerBinding? = null
    private val binding get() = _binding!!
    val pets = mutableListOf<Pets>()
    lateinit var parentActivity:Activity
    val bundle:Bundle?=arguments
    val setbundle = Bundle()
    var mode:Int=0
    var fsize:Int=0
    lateinit var responseGetPets: Call<getPets>
    var friendId= mutableListOf<Long>()
    val passBlankFragment = BlankFragment()
    //val passPetListRecyclerFragment=PetListRecyclerFragment()
    //    val petsittercards= mutableListOf<PetsitterCard>()
//    val petsitterListAdapter=PetsitterListAdapter(petsittercards)
    val petListAdapter=PetListAdapter(pets)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("TAG11",ChooseFriendFragment.Companion.mode.toString())
        Log.d("TAG11","start PetListRecyclerFrag")
        //유저 펫리스트 불러오기
        responseGetPets=apiServer.getPets(Cookie.userId)
        //responseGetPets=apiServer.getPets(4)


        //유저 Cookie.userId 펫리스트불러오기
        //val responseGetPets: Call<getPetsDTO> = apiServer.getPets(Cookie.userId)
        _binding = FragmentPetListRecyclerBinding.inflate(inflater,container,false)

        responseGetPets.enqueue(object : Callback<getPets> {
            override fun onResponse(
                call: Call<getPets>,
                response: Response<getPets>
            ) {
                Log.d("TAG11","성공")
                val targetPets=response.body()!!.result
                for (pet in targetPets) {
                    pets.add(Pets(pet.petName, pet.petSpecies, pet.petBirth, pet.petSize))
                    Log.d("TAG11","번째")
                }
                binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
                binding.petListRecyclerView.adapter=petListAdapter
            }

            override fun onFailure(call: Call<getPets>, t: Throwable) {
                Log.d("TAG11", "실패 : $t")
            }
        })

        //        petsitterListAdapter.setItemClickListener(object : PetsitterListAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                loadFragment(PetListRecyclerFragment())
//            }
//
//        })
        petListAdapter.setItemClickListener(object :PetListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
            }

        })
        binding.reservation00NextBtn.setOnClickListener{
            if(ChooseFriendFragment.Companion.mode==1) {
                Log.d("TAG11","fsize = "+fsize)
                passBlankFragment.arguments = setbundle
                loadFragment(passBlankFragment)
            }
            else{
                loadFragment(Reservation01Fragment())
            }

        }
        Log.d("testcode","??")


        return binding.root
    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()

    }

}
