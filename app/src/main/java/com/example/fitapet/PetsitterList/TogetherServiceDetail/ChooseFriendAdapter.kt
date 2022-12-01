package com.example.fitapet.PetsitterList.TogetherServiceDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitapet.R
import com.example.fitapet.databinding.ChooseFriendListBinding
import com.example.fitapet.navfragment.FriendCard

class ChooseFriendAdapter(val friendcard:MutableList<FriendCard>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: ChooseFriendListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ChooseFriendListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        Glide.with(holder.itemView).load(friendcard[position].friendImg).into(binding.FriendImage)
//        binding.FriendImage.setImageResource(R.drawable.example1)
        binding.FriendName.text=friendcard[position].friendName
        binding.FriendEmail.text=friendcard[position].firendEmail
        holder.itemView.setOnClickListener {
            Log.d("Check","clickb!")
            itemClickListener.onClick(it, position)
            Log.d("Check","clickc!")
            binding.chooseFriendGrid.isSelected=binding.chooseFriendGrid.isSelected!=true

            Log.d("Check","clickd!")
        }
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int){

        }
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return friendcard.size
    }
}