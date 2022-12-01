package com.example.fitapet.wonjune

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.fitapet.Cookie
import com.example.fitapet.MainActivity
import com.example.fitapet.PetsitterList.ServiceEvaluatePageFragment
import com.example.fitapet.R
import com.example.fitapet.databinding.ActivityMyPgBinding
import com.example.fitapet.login.LoginActivity
import com.example.fitapet.navfragment.MypageFragment
import com.example.fitapet.retrofit.RetrofitClient.apiServer
import com.example.fitapet.retrofit.dto.getCurrentServiceDTO
import com.example.fitapet.retrofit.dto.ingPets
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPgActivity : AppCompatActivity() {
//    lateinit var btnLoc : Button
//    lateinit var btnVideo : Button
    lateinit var petList : RecyclerView
    var parray = listOf<ingPets>()
    lateinit var guardianName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 액션바 왼쪽에 버튼 만들기(defalut:뒤로가기버튼)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        supportActionBar?.setTitle("진행중인 서비스")
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀바 없애기
        val responseGetCurrentService=apiServer.getCurrentService(Cookie.userId)

        val binding = ActivityMyPgBinding.inflate(layoutInflater)
        petList = binding.ingPetList


        responseGetCurrentService.enqueue(object : Callback<getCurrentServiceDTO> {
            override fun onResponse(
                call: Call<getCurrentServiceDTO>,
                response: Response<getCurrentServiceDTO>
            ) {
                Log.d(ContentValues.TAG, "성공 : ${response.raw()}")
                Log.d("getCurrentService", response.body()!!.isSuccess)
                Log.d("getCurrentService", response.body()!!.code.toString())
                Log.d("getCurrentService", response.body()!!.result.toString())


                val responseResult=response.body()!!.result
                Log.d("getCurrentService", responseResult.toString())
                binding.txtView1.text=responseResult.petSitterName+" 펫시터"
                binding.txtView3.text=responseResult.planStartTime
                binding.txtView5.text=responseResult.planStartTime
                binding.editRequest.text=responseResult.customerRequestContent
                guardianName = responseResult.customerName
                parray = responseResult.pets as MutableList<ingPets>
                petList.adapter?.notifyDataSetChanged()
//                for (pet in targetPets)
////                  val petName:String,val petBreed:String, val petBirth:String,val petSize
//                    pets.add(Pets(pet.petName,pet.petSpecies,pet.petBirth,pet.petSize))

//
//                binding.petListRecyclerView.layoutManager= LinearLayoutManager(requireContext())
//                binding.petListRecyclerView.adapter= PetListAdapter(pets)
            }

            override fun onFailure(call: Call<getCurrentServiceDTO>, t: Throwable) {
                Log.d("getCurrentService",t.toString())
            }
        })
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀바 없애기
        setContentView(binding.root)
//        btnLoc = findViewById<Button>(R.id.getLoc)
//        btnVideo = findViewById<Button>(R.id.getVideo)
//

        binding.getVideo.setOnClickListener {
            var intent = Intent(applicationContext, MaiActivity::class.java)
            startActivity(intent)
        }
        binding.getLoc.setOnClickListener {
            var intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
        }
//       binding.goEvalutate.setOnClickListener {
//           var intent = Intent(applicationContext, ServiceEvaluatePageFragment::class.java)
//           startActivity(intent)
//       }

//       val button = findViewById<Button>(R.id.go_evalutate);
//           button.setOnClickListener{
//
//        }
        petList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        petList.adapter = RecyclerviewAdapter()
    }
    inner class RecyclerviewAdapter : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingpet, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var strimg = parray[position].profileImg //https://붙혀서 보내야함
            Glide.with(this@MyPgActivity).load(strimg).into(holder.itemImg)
            //holder.itemImg.setImageResource(R.drawable.petimg)
            holder.itemGName.text = guardianName //보호자 이름을 어디서??
            holder.itemPName.text = parray[position].petName
            holder.itemSpecies.text = parray[position].petSpecies
            holder.itemSize.text = parray[position].petSize
            holder.itemType.text = parray[position].petType

            if (parray[position].petSex == "MALE"){
                holder.itemSex.text = "남"
            }else{
                holder.itemSex.text = "여"
            }

            holder.itemAge.text = parray[position].petAge.toString()+"살"
        }

        override fun getItemCount(): Int {
            return parray.size
        }

        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val itemImg = view.findViewById<CircleImageView>(R.id.circleImageView)
            val itemGName = view.findViewById<TextView>(R.id.guardianName)
            val itemPName = view.findViewById<TextView>(R.id.petName)
            val itemType = view .findViewById<TextView>(R.id.catORdog)
            val itemSpecies = view.findViewById<TextView>(R.id.textSpecies)
            val itemSize = view.findViewById<TextView>(R.id.textSize)
            val itemSex = view.findViewById<TextView>(R.id.textSex)
            val itemAge = view.findViewById<TextView>(R.id.textAge)
        }

    }

    override fun onBackPressed() {
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
    override fun onSupportNavigateUp(): Boolean {
        Log.d("actionbar","onSupportNabigateUp")
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }
}