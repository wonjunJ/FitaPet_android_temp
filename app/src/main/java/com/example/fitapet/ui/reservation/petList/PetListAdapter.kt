package com.example.fitapet.ui.reservation.petList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.PetsitterList.PetsitterListAdapter
import com.example.fitapet.databinding.PetItemMainBinding

class PetListAdapter(val pets: MutableList<Pets>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: PetItemMainBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(PetItemMainBinding.inflate(LayoutInflater.from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.petListName.text=pets[position].petName
        binding.petListBreed.text=pets[position].petBreed
        binding.petListBirth.text=pets[position].petBirth
        binding.petListSize.text=pets[position].petSize
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
            binding.petListLayout.isSelected=binding.petListLayout.isSelected!=true
        }
    }

    override fun getItemCount(): Int {
        return pets.size
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int){

        }
    }
    fun setItemClickListener(onItemClickListener: PetListAdapter.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : PetListAdapter.OnItemClickListener

}