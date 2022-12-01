package com.example.fitapet.PetsitterList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitapet.R
import com.example.fitapet.databinding.PetItemMainBinding
import com.example.fitapet.databinding.DogServiceListBinding
import com.example.fitapet.retrofit.dto.Petsitter
import com.example.fitapet.ui.reservation.petList.PetListAdapter

class PetsitterListAdapter(val petsittercard:MutableList<PetsitterCard>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: DogServiceListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PetsitterListAdapter.MyViewHolder(
            DogServiceListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PetsitterListAdapter.MyViewHolder).binding
        var strimg = petsittercard[position].img
        Glide.with(holder.itemView).load(strimg).into(binding.petsitterImage)
        //binding.petsitterImage.setImageResource(R.drawable.example1)
        binding.petsitterName.text=petsittercard[position].name
        binding.petsitterCareer.text=petsittercard[position].career+"년"
        binding.petsitterHavePet.text=petsittercard[position].havepet
        if (petsittercard[position].gender == "F"){
            binding.petsitterGender.text = "여"
        }else{
            binding.petsitterGender.text = "남"
        }
        binding.petsitterAge.text=petsittercard[position].age+"세"
        binding.PetsitterText.text=petsittercard[position].petsitterText
        if (petsittercard[position].isAgreeToFilm_YN == "Y"){
            binding.camera.text = "촬영 동의"
        }else{
            binding.camera.text = "촬영 불가"
        }

        if (petsittercard[position].isWalkable_YN == "Y"){
            binding.takeawalk.text = "산책 가능"
        }else{
            binding.takeawalk.text = "산책 불가능"
        }

        if (petsittercard[position].isAgreeSharingLocation_YN == "Y"){
            binding.location.text = "위치 공유 동의"
        }else{
            binding.location.text = "위치 공유 불가"
        }

        if (petsittercard[position].isPossibleCareOldPet_YN == "Y"){
            binding.olddog.text = "노견 케어 가능"
        }else{
            binding.olddog.text = "노견 케어 불가"
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return petsittercard.size
    }
}
