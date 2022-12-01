package com.example.fitapet.navfragment

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.fitapet.MainActivity

import com.example.fitapet.Cookie

import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentFriendBinding
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.Friend
import com.example.fitapet.retrofit.dto.FriendDTO
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendFragment : Fragment() {
    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!
    val friendcards= mutableListOf<FriendCard>()
    val friendListAdapter= FriendListAdapter(friendcards)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(inflater,container,false)
//        var actionBar = (activity as MainActivity?)!!.supportActionBar
//        actionBar?.setTitle("친구 목록")
        //actionBar?.setCustomView(R.id.menu_friend)

//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)
        val responseGetFriends= RetrofitClient.apiServer.getFriends(Cookie.userId)
        responseGetFriends.enqueue(object : Callback<FriendDTO> {
            override fun onResponse(call: Call<FriendDTO>, response: Response<FriendDTO>) {
                //bundle.putInt("mode",1)
                val responseResult=response.body()!!.result
                for(i:Int in 0..responseResult.size-1){
                    val friend=responseResult.get(i)
                    Log.d("TAG11",friend.customerName)
                    friendcards.add(FriendCard(friendImg = friend.profileImgUrl, friendName = friend.customerName, firendEmail = friend.kakaoEmail))
                    Log.d("TAG11","DONE")
                    Log.d("TAG11",""+friendcards.size)

                }
                binding.friendRecyclerview.adapter=friendListAdapter
               // binding.friendChooseRecyclerview.adapter=chooseFriendAdapter
               // bundle.putInt("fSize",responseResult.size)
                //val passPetListFragment = PetListRecyclerFragment()
               // passPetListFragment.arguments=bundle
            }
            override fun onFailure(call: Call<FriendDTO>, t: Throwable) {
            }
        })
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))

        binding.friendRecyclerview.layoutManager= LinearLayoutManager(requireContext())

        friendListAdapter.setItemClickListener(object : FriendListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                loadFragment(PetListRecyclerFragment())
            }

        })

        return binding.root
    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}