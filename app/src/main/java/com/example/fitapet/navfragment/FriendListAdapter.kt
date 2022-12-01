package com.example.fitapet.navfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitapet.R
import com.example.fitapet.databinding.FriendListBinding

class FriendListAdapter(val friendcard:MutableList<FriendCard>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(val binding: FriendListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            FriendListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        Glide.with(holder.itemView).load(friendcard[position].friendImg).into(binding.FriendImage)
        binding.FriendImage.setImageResource(R.drawable.example1)
        binding.FriendName.text=friendcard[position].friendName
        binding.FriendEmail.text=friendcard[position].firendEmail
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    }
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return friendcard.size
    }
}
