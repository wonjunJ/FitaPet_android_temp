package com.example.fitapet.PetsitterList.TogetherServiceDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitapet.Cookie
import com.example.fitapet.PetsitterList.TogetherServiceDetail.ChooseFriendAdapter
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentChooseFriendBinding
import com.example.fitapet.navfragment.FriendCard
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.Friend
import com.example.fitapet.retrofit.dto.FriendDTO
import com.example.fitapet.ui.reservation.petList.BlankFragment
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseFriendFragment : Fragment() {
    private var _binding: FragmentChooseFriendBinding? = null
    private val binding get() = _binding!!
    val friendcards= mutableListOf<FriendCard>()
    val chooseFriendAdapter= ChooseFriendAdapter(friendcards)
    val bundle = Bundle()
    var cnt:Int=0
    lateinit var friend:Friend
    var friendId= mutableListOf<Long>()
    val passPetListRecyclerFragment = PetListRecyclerFragment()
    val passFriendId= mutableListOf<Long>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Companion.mode=1
        _binding = FragmentChooseFriendBinding.inflate(inflater,container,false)
//        var actionBar = (activity as MainActivity?)!!.supportActionBar
//        actionBar?.setTitle("친구 목록")
        //actionBar?.setCustomView(R.id.menu_friend)
        passPetListRecyclerFragment.arguments=bundle
//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)
        val responseGetFriends= RetrofitClient.apiServer.getFriends(Cookie.userId)
        responseGetFriends.enqueue(object : Callback<FriendDTO> {
            override fun onResponse(call: Call<FriendDTO>, response: Response<FriendDTO>) {
                bundle.putInt("mode",1)
                val responseResult=response.body()!!.result
                for(i:Int in 0..responseResult.size-1){
                    friend=responseResult.get(i)
                    Log.d("TAG11",friend.customerName)
                    friendcards.add(FriendCard(friendImg = friend.profileImgUrl, friendName = friend.customerName, firendEmail = friend.kakaoEmail))
                    Log.d("TAG11","DONE")
                    Log.d("TAG11",""+friendcards.size)
                    friendId.add(friend.friendId.toLong())
                }
                binding.friendChooseRecyclerview.adapter=chooseFriendAdapter
                bundle.putInt("fsize",2)
            }
            override fun onFailure(call: Call<FriendDTO>, t: Throwable) {
            }
        })

        Log.d("TAG11",""+friendcards.size)
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","김도선","이메일@knu.ac.kr",))
//        friendcards.add(FriendCard("R.drawable.example1","정민욱","이메일@knu.ac.kr",))
        Log.d("TAG11",""+friendcards.size)


        binding.friendChooseRecyclerview.layoutManager= LinearLayoutManager(requireContext())

        chooseFriendAdapter.setItemClickListener(object : ChooseFriendAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                if(cnt==0){
                    Companion.friendIdCom1=friendId.get(position)
                    cnt++
                }
                else {
                    Companion.friendIdCom2 = friendId.get(position)
                }
                //bundle.putLong("f${cnt++}",friendId.get(position))
                Log.d("TAG11","f"+"${cnt-1}"+", id = "+friendId.get(position))
                Log.d("TAG11","bundle get = "+bundle.getLong("f0"))
            }

        })

        binding.goNextPage.setOnClickListener {

            Log.d("TAG11",""+bundle.getInt("mode",0))
            loadFragment(passPetListRecyclerFragment)
        }

        return binding.root
    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    companion object{
        var friendIdCom1:Long?=0
        var friendIdCom2:Long?=0
        var mode:Int=0
    }
}

