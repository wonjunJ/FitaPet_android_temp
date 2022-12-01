package com.example.fitapet.ui.reservation.petList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.PetsitterList.PetsitterListAdapter
import com.example.fitapet.databinding.PetItemMain2Binding
import com.example.fitapet.databinding.PetItemMain3Binding
import com.example.fitapet.databinding.PetItemMainBinding

class PetListAdapter3(val pets: MutableList<Pets>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder2(val binding: PetItemMain3Binding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder2(PetItemMain3Binding.inflate(LayoutInflater.from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder2).binding
        binding.petListName.text=pets[position].petName
        binding.petListBreed.text=pets[position].petBreed
        binding.petListBirth.text=pets[position].petBirth
        binding.petListSize.text=pets[position].petSize
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
            binding.petListLayout3.isSelected=binding.petListLayout3.isSelected!=true
        }
    }

    override fun getItemCount(): Int {
        return pets.size
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int){

        }
    }
    fun setItemClickListener(onItemClickListener: PetListAdapter3.OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : PetListAdapter3.OnItemClickListener

}