package com.example.fitapet.PetsitterList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.R

//class PetsitterRecyclerview(items: ArrayList<PetsitterCard>) : RecyclerView.Adapter<PetsitterRecyclerview.MyViewHolder>() {

//    var items = items
//
//    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var ivProfile: ImageView = itemView.findViewById(R.id.petsitterImage)
//        var tvName: TextView = itemView.findViewById(R.id.petsitterName)
//        var tvcareer: TextView = itemView.findViewById(R.id.petsitterCareer)
//        var tvhavepet: TextView = itemView.findViewById(R.id.petsitterHavePet)
//        var tvgender: TextView = itemView.findViewById(R.id.petsitterGender)
//        var tvage: TextView = itemView.findViewById(R.id.petsitterAge)
//        var tvpetsittertext: TextView = itemView.findViewById(R.id.PetsitterText)
//
//
//        fun bind(position: Int) {
//            ivProfile.setImageResource(items[position].imageRes)
//            ivProfile.setBackgroundResource(items[position].backgroundRes)
//            tvName.text = items[position].name
//            tvcareer.text = items[position].career
//            tvhavepet.text = items[position].havepet
//            tvgender.text = items[position].gender
//            tvage.text = items[position].age
//            tvpetsittertext.text = items[position].petsitterText
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val context = parent.context
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view: View = inflater.inflate(R.layout.dog_service_list, parent, false)
//
//        return MyViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int = items.size
//}