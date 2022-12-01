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
import com.example.fitapet.databinding.FragmentServiceEvaluatePageBinding
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment

class ServiceEvaluatePageFragment : Fragment() {
    private var _binding: FragmentServiceEvaluatePageBinding? = null
    private val binding get() = _binding!!
    val evaluateCards= mutableListOf<EvaluateCard>()
//    val petsitterListAdapter=PetsitterListAdapter(petsittercards)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceEvaluatePageBinding.inflate(inflater,container,false)
//        binding.petListRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.petListRecyclerView.adapter= PetListAdapter(pets)

    evaluateCards.add(EvaluateCard("R.drawable.example1","안녕하세요"))
    evaluateCards.add(EvaluateCard("R.drawable.example1","안녕하세요"))
//        binding.recylcerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.recylcerView.adapter=petsitterListAdapter
//        petsitterListAdapter.setItemClickListener(object : PetsitterListAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int) {
//                loadFragment(PetListRecyclerFragment())
//            }
//
//        })

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