package com.example.fitapet.navfragment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitapet.R
import com.example.fitapet.databinding.PetItemMainBinding
import com.example.fitapet.databinding.FragmentFriendBinding
import com.example.fitapet.databinding.FriendListBinding
import com.example.fitapet.databinding.ReviewHomeBinding
import com.example.fitapet.navfragment.FriendCard
import com.example.fitapet.navfragment.FriendFragment

class ReviewListAdapter(val reviewCard:MutableList<ReviewCard>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: ReviewHomeBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewListAdapter.MyViewHolder(
            ReviewHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("LOGTest",""+itemCount+"---"+position)
        val binding = (holder as ReviewListAdapter.MyViewHolder).binding
        if(position==0){
            binding.parentImage.setImageResource(R.drawable.uk)
        }
        else if(position==1){
            binding.parentImage.setImageResource(R.drawable.doseon_kim02)
        }else{
            binding.parentImage.setImageResource(R.drawable.example1)
        }
        Glide.with(holder.itemView).load(reviewCard[position].profileImg).into(binding.parentImage)
        binding.parentName.text=reviewCard[position].parentName
        binding.reviewText.text=reviewCard[position].reviewText
        Glide.with(holder.itemView).load(reviewCard[position].petsitterImg).into(binding.petsitterImg)
//        binding.petsitterImg.setImageResource(R.drawable.example1)
        binding.whatService.text=reviewCard[position].whatservice
        binding.petsittername.text=reviewCard[position].petsitterName
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
        return reviewCard.size
    }
}