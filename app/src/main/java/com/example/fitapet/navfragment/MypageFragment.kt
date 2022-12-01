package com.example.fitapet.navfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.fitapet.PetsitterList.ProfileEditFragment
import com.example.fitapet.PetsitterList.TogetherServiceDetail.TogetherServiceFragment

import com.example.fitapet.Cookie

import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentMypageBinding
import com.example.fitapet.retrofit.RetrofitClient
import com.example.fitapet.retrofit.dto.*
import com.example.fitapet.ui.animalReg.AnimalRegFragment
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    lateinit var petList: RecyclerView
    lateinit var petSitter: RecyclerView
    var parray = mutableListOf<MypetList>()
    var myPets = listOf<Pets>()
    var myBmark = listOf<Bookmark>()

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater,container,false)
        petList = binding.petList
        petSitter = binding.petSitterList
        Log.d("idHERE1", Cookie.userId.toString())

        RetrofitClient.apiServer.getSimple(Cookie.userId).enqueue(object: Callback<getUser>{
            override fun onResponse(call: Call<getUser>, response: Response<getUser>) {
                val responseResult=response.body()!!.result
                Log.d("HERE5", responseResult.size.toString())
                var strimg = responseResult[0].profileImgUrl
                Glide.with(this@MypageFragment).load(strimg).into(binding.profImg)
                binding.ownerName.text = responseResult[0].customerName
                binding.myEmail.text = responseResult[0].kakaoEmail
            }

            override fun onFailure(call: Call<getUser>, t: Throwable) {
                Log.d("HERE3", "여기3")
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })


        RetrofitClient.apiServer.getPets(Cookie.userId).enqueue(object: Callback<getPets>{
            override fun onResponse(call: Call<getPets>, response: Response<getPets>) {
                Log.d("HERE2", "여기2")
                myPets = response.body()!!.result
                Log.d("HEREsize", myPets.size.toString())
                petList.adapter?.notifyDataSetChanged()
                //Log.d("HERE2", "여기2")
            }

            override fun onFailure(call: Call<getPets>, t: Throwable) {
                Log.d("HERE3", "여기3")
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        /*
        RetrofitClient.apiServer.getBmark(4).enqueue(object: Callback<getBmark>{
            override fun onResponse(call: Call<getBmark>, response: Response<getBmark>) {
                myBmark = response.body()!!.result
                Log.d("HEREsize2", myBmark.size.toString())
                petSitter.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<getBmark>, t: Throwable) {
                Log.d("HERE6", "여기6")
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
         */


        Log.d("HERE4","여기4")
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        petList.layoutManager = linearLayoutManager
        petList.adapter = RecyclerviewAdapter()
        binding.editProf.setOnClickListener {
            loadFragment(ProfileEditFragment())
        }

        binding.addPet.setOnClickListener {
            loadFragment(AnimalRegFragment())
        }

        return binding.root
    }
    inner class RecyclerviewAdapter : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //var strimg = myPets[position]
            Log.d("HERE7", "여기는 왔는데")
            val strimg = myPets[position].profileImg
            Glide.with(this@MypageFragment).load(strimg).into(holder.itemImg)
            //holder.itemImg.setImageResource(R.drawable.petimg)
            holder.itemPname.text = myPets[position].petName
            holder.itemSpecies.text = myPets[position].petSpecies
            holder.itemSize.text = myPets[position].petSize
            if (myPets[position].petSex == "MALE"){
                holder.itemSex.text = "남"
            }else{
                holder.itemSex.text = "여"
            }
            holder.itemAge.text = myPets[position].petAge.toString()+"살"
        }

        override fun getItemCount(): Int {
            return myPets.size
        }

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val itemPname = view.findViewById<TextView>(R.id.textPetname)
            val itemSpecies = view.findViewById<TextView>(R.id.textSpecies)
            val itemSize = view.findViewById<TextView>(R.id.textSize)
            val itemSex = view.findViewById<TextView>(R.id.textSex)
            val itemAge = view.findViewById<TextView>(R.id.textAge)
            val itemImg = view.findViewById<ImageView>(R.id.pet_img) //펫이미지 못 가져옴
        }

    }

    inner class RecyclerviewAdapter2 :RecyclerView.Adapter<RecyclerviewAdapter2.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter2.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bmark, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemPSname.text = myBmark[position].petSitterName
            if (myBmark[position].sex == "M"){
                holder.itemSex.text = "남"
            }else{
                holder.itemSex.text = "여"
            }

            if (myBmark[position].age == null){
                holder.itemAge.text = "비공개"
            }else{
                holder.itemAge.text = myBmark[position].age.toString()+"세"
            }

            if (myBmark[position].careType == null){
                holder.itemKind.text = "비공개"
            }else{
                holder.itemKind.text = myBmark[position].careType
            }

            var filtering = mutableListOf<String>()
            var i = 0
            if (myBmark[position].isAgreeSharingLocation_YN == "Y"){
                filtering.add("위치 공유 가능")
            }
            if (myBmark[position].isAgreeToFilm_YN == "Y"){
                filtering.add("촬영 가능")
            }
            if (myBmark[position].isPossibleCareOldPet_YN == "Y"){
                filtering.add("노견 케어")
            }
            if (myBmark[position].isWalkable_YN == "Y"){
                filtering.add("산책 가능")
            }
            holder.itemRpon.text = filtering[0]
            var str = ""
            for (k in 1 until filtering.size){
                str += filtering[k]+" "
            }
            holder.itemRpon2.text = str
        }

        override fun getItemCount(): Int {
            return myBmark.size
        }

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val itemPSimg = view.findViewById<CircleImageView>(R.id.sitterProfile)
            val itemPSname = view.findViewById<TextView>(R.id.sitterName)
            val itemKind = view.findViewById<TextView>(R.id.textKind)
            val itemRpon = view.findViewById<TextView>(R.id.textResponse)
            val itemRpon2 = view.findViewById<TextView>(R.id.textResponse2)
            val itemSex = view.findViewById<TextView>(R.id.textSex2)
            val itemAge = view.findViewById<TextView>(R.id.textAge2)
        }
    }

    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}