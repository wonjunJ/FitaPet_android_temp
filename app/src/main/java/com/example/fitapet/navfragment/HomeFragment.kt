package com.example.fitapet.navfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.PetsitterList.*
import com.example.fitapet.PetsitterList.TogetherServiceDetail.TogetherServiceFragment
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentHomeBinding
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.ReviewDTO
import com.example.fitapet.retrofit.dto.getAddr
import com.example.fitapet.retrofit.dto.getStatus
import com.example.fitapet.wonjune.WarnDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val reviewscards = mutableListOf<ReviewCard>()
    val reviewListAdapter=ReviewListAdapter(reviewscards)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val responseGetReviews= RetrofitClient.apiServer.getRevies()
        responseGetReviews.enqueue(object : Callback<ReviewDTO>{
            override fun onResponse(call: Call<ReviewDTO>, response: Response<ReviewDTO>) {
                val responseResult=response.body()!!.result
                for(i:Int in 0..responseResult.size-1){
                    val review=responseResult.get(i)
                    //Log.d("TAG11",friend.customerName)
                    reviewscards.add(ReviewCard(review.profileImgOfCustomer,review.customerName,review.reviewContent,review.petSitterProfileImg,review.category,review.petSitterName,review.category))
                    Log.d("TAG11","DONE")

                }
                binding.reviewRecylcerView.adapter=reviewListAdapter
            }

            override fun onFailure(call: Call<ReviewDTO>, t: Throwable) {

            }
        })

        val dialog = WarnDialog()
        dialog.isCancelable=false
        dialog.show(activity?.supportFragmentManager!!, "WarnDialog")
        RetrofitClient.apiServer.getAddr(4).enqueue(object: Callback<getAddr>{
            override fun onResponse(call: Call<getAddr>, response: Response<getAddr>) {
                val txtAddress = response.body()!!.result
                if (txtAddress.address == null){
                    binding.myAddr.text = "주소를 입력해주세요"
                }else{
                    binding.myAddr.text = txtAddress.address
                }
            }

            override fun onFailure(call: Call<getAddr>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

//        reviewscards.add(ReviewCard("R.drawable.uk","정민욱","보내주신 영상 보니까 강아지랑 정말 잘 놀아주시더라구요 ㅠㅠ 우리 똘이 표정보면 똘이한테 잘 해주신게 느껴지더라구요 ㅠㅠㅎㅎㅎㅎ","R.drawable.example1","강아지돌봄","이찬수"))
//        reviewscards.add(ReviewCard("R.drawable.doseon_kim02","김도선","마음 놓고 밖에 갔다올 수 있었어요","R.drawable.example1","고양이돌봄","김동근"))
//        reviewscards.add(ReviewCard("R.drawable.example1","박희원","기대했던것보다 훨씬 친절","R.drawable.example1","강아지돌봄","이찬혁"))
//        reviewscards.add(ReviewCard("R.drawable.example1","정원준","고양이가 좋아하더라구요","R.drawable.example1","고양이돌봄","정민욱"))
//        reviewscards.add(ReviewCard("R.drawable.example1","김도현","가성비 최고에요","R.drawable.example1","강아지돌봄","김도선"))
//        reviewscards.add(ReviewCard("R.drawable.example1","김도원","친구","R.drawable.example1","고양이돌봄","정민욱"))

        binding.reviewRecylcerView.layoutManager= LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)


        reviewListAdapter.setItemClickListener(object : ReviewListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                loadFragment(ReviewPageFragment())
            }

        })

        binding.dogServices.setOnClickListener {
            RetrofitClient.apiServer.getStatus(4).enqueue(object: Callback<getStatus>{
                override fun onResponse(call: Call<getStatus>, response: Response<getStatus>) {
                    val now = response.body()!!.result
                    if (now.status == "COMPLETED"){
                        loadFragment(DogServiceFragment())
                    }else{
                        val dialog = WarnDialog()
                        dialog.isCancelable=false
                        dialog.show(activity?.supportFragmentManager!!, "WarnDialog")
                        //var dialog = AlertDialog.Builder(requireContext())
                        //dialog.setTitle("알림")
                        //dialog.setMessage("돌봄 서비스 진행을 위해서는 펫 등록을 먼저 진행해주세요")
                        //dialog.show()
                    }
                }

                override fun onFailure(call: Call<getStatus>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        binding.catServices.setOnClickListener {
            RetrofitClient.apiServer.getStatus(4).enqueue(object: Callback<getStatus>{
                override fun onResponse(call: Call<getStatus>, response: Response<getStatus>) {
                    val now = response.body()!!.result
                    if (now.status == "COMPLETED"){
                        loadFragment(CatServiceFragment())
                    }else{
                        val dialog = WarnDialog()
                        dialog.isCancelable=false
                        dialog.show(activity?.supportFragmentManager!!, "WarnDialog")
                        /*var dialog = AlertDialog.Builder(requireContext())
                        dialog.setTitle("알림")
                        dialog.setMessage("돌봄 서비스 진행을 위해서는 펫 등록을 먼저 진행해주세요")
                        dialog.show()*/
                    }
                }

                override fun onFailure(call: Call<getStatus>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        binding.reviews.setOnClickListener {
            loadFragment(ReviewPageFragment())
        }

        binding.togetherServices.setOnClickListener {
            RetrofitClient.apiServer.getStatus(4).enqueue(object: Callback<getStatus>{
                override fun onResponse(call: Call<getStatus>, response: Response<getStatus>) {
                    val now = response.body()!!.result
                    if (now.status == "COMPLETED"){
                        loadFragment(TogetherServiceFragment())
                    }else{
                        val dialog = WarnDialog()
                        dialog.isCancelable=false
                        dialog.show(activity?.supportFragmentManager!!, "WarnDialog")
                        /*var dialog = AlertDialog.Builder(requireContext())
                        dialog.setTitle("알림")
                        dialog.setMessage("돌봄 서비스 진행을 위해서는 펫 등록을 먼저 진행해주세요")
                        dialog.show()*/
                    }
                }

                override fun onFailure(call: Call<getStatus>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
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
}
