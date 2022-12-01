package com.example.fitapet.PetsitterList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentDogServiceBinding
import com.example.fitapet.databinding.FragmentReviewPageBinding
import com.example.fitapet.navfragment.ReviewCard
import com.example.fitapet.navfragment.ReviewDetailAdapter
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.ReviewDTO
import com.example.fitapet.retrofit.dto.ReviewDetailDTO
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewPageFragment : Fragment() {
    private var _binding: FragmentReviewPageBinding? = null
    private val binding get() = _binding!!
    val reviewDetailCards= mutableListOf<ReviewDetailCard>()
    val reviewDetailAdapter=ReviewDetailAdapter(reviewDetailCards)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewPageBinding.inflate(inflater,container,false)
//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)
        val responseGetReviews= RetrofitClient.apiServer.getReviewDetail()
        responseGetReviews.enqueue(object : Callback<ReviewDetailDTO> {
            override fun onResponse(call: Call<ReviewDetailDTO>, response: Response<ReviewDetailDTO>) {
                val responseResult=response.body()!!.result
                for(i:Int in 0..responseResult.size-1){
                    val review=responseResult.get(i)
                    //Log.d("TAG11",friend.customerName)
                    reviewDetailCards.add(ReviewDetailCard(review.customerName,review.profileImgOfCustomer
                        ,review.reviewPicture,review.isLike_YN,review.reviewContent,review.petType,review.petSitterName,review.petSitterProfileImg))
                    Log.d("TAG11","DONE")
                }
                binding.reviewPageRecylcerView.adapter=reviewDetailAdapter
            }

            override fun onFailure(call: Call<ReviewDetailDTO>, t: Throwable) {
                Log.d("TAG11","fail to call api")
            }
        })
//        reviewDetailCards.add(ReviewDetailCard("R.drawable.example1","김도선","R.drawable.example1","굿굿굿","R.drawable.example1","강아지돌봄","정민욱"))
//        reviewDetailCards.add(ReviewDetailCard("R.drawable.example1","정민욱","R.drawable.example1","아주 좋아요","R.drawable.example1","고양이돌봄","김도선"))
//        reviewDetailCards.add(ReviewDetailCard("R.drawable.example1","김도선","R.drawable.example1","굿굿굿","R.drawable.example1","함께돌봄","정민욱"))
//        reviewDetailCards.add(ReviewDetailCard("R.drawable.example1","정민욱","R.drawable.example1","아주 좋아요","R.drawable.example1","고양이돌봄","김도선"))
        binding.reviewPageRecylcerView.layoutManager=LinearLayoutManager(requireContext())

        reviewDetailAdapter.setItemClickListener(object : ReviewDetailAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
//                loadFragment(PetListRecyclerFragment())
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