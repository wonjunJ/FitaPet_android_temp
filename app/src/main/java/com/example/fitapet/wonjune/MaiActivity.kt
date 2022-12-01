package com.example.fitapet.wonjune

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitapet.MainActivity
import com.example.fitapet.R
import com.example.fitapet.databinding.ActivityMaiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MaiActivity : AppCompatActivity() {
    lateinit var txtlist : TextView
    lateinit var btnremon : Button
    lateinit var videoList : RecyclerView
    var varray = mutableListOf<OrderedDict>()
    var filename: String = ""
    var retrofit = Retrofit.Builder()
        .baseUrl("http://118.45.212.21:8000")
        //"http://192.168.0.195:8000" 우리집
        //"http://118.45.212.21:8000" 자취방 공용IP
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMaiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 액션바 왼쪽에 버튼 만들기(defalut:뒤로가기버튼)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        supportActionBar?.setTitle("영상보기")
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MODE_PRIVATE)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), MODE_PRIVATE)

        var uploadService = retrofit.create(API_upload::class.java)
        btnremon = findViewById<Button>(R.id.button3)
        videoList = findViewById<RecyclerView>(R.id.video_list)

    //레트로핏 서버요청
        uploadService.sendFile("1").enqueue(object: Callback<List<OrderedDict>> {
            override fun onResponse(
                call: Call<List<OrderedDict>>,
                response: Response<List<OrderedDict>>
            ) {
                varray = response.body() as MutableList<OrderedDict>
                //if (varray != null) {
                //    vlist = varray
                //}
                /*
                for(data in varray!!){
                    Log.d("TEST", data.title)
                }
                 */
                videoList.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<OrderedDict>>, t: Throwable) {
                var dialog = AlertDialog.Builder(this@MaiActivity)
                dialog.setTitle("에러")
                dialog.setMessage("호출실패했습니다." + t.message)
                dialog.show()
            }

        })

        btnremon.setOnClickListener {
            var intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("channelId", "testw-1")
            Log.d("test","videoClick")
            startActivity(intent)
        }
        videoList.layoutManager = LinearLayoutManager(this)
        videoList.adapter = RecyclerviewAdapter()
    }
    inner class RecyclerviewAdapter : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
            return ViewHolder(view)
        }
        override fun getItemCount(): Int {
            return varray.size
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemTitle.text = varray[position].title
            holder.itemDate.text = varray[position].dateTimeOfUpload
            holder.itemSize.text = varray[position].fileSize.toString()+"MB"
            holder.itemImg.setImageBitmap(StringToBitmap(varray[position].thumnail))
            holder.itemView.setOnClickListener{
                var intent = Intent(applicationContext, DownActivity::class.java)
                intent.putExtra("title",varray[position].title)
                startActivity(intent)
            }
        }
        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val itemTitle = view.findViewById<TextView>(R.id.item_title)
            val itemDate = view.findViewById<TextView>(R.id.item_date)
            val itemSize = view.findViewById<TextView>(R.id.item_size)
            val itemImg = view.findViewById<ImageView>(R.id.imageView)
        }

        fun StringToBitmap(encodedString: String): Bitmap? {
            try {
                var encodedByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
                var bitmap: Bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.size)
                return bitmap
            } catch (e: Exception) {
                e.message
                return null
            }
        }



//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            holder.itemTitle.text = varray[position].title
//            holder.itemDate.text = varray[position].dateTimeOfUpload
//            holder.itemSize.text = varray[position].fileSize.toString()+"MB"
//            holder.itemImg.setImageBitmap(StringToBitmap(varray[position].thumnail))
//            /*
//            var videoThumbnailDisposable = io.reactivex.Observable.just("${varray[position].uploadedFile}")
//                .subscribeOn(Schedulers.io())
//                .subscribe {
//                    holder.itemImg.load(
//                        VideoThumbnailUtil().getWebVideoThumbnail(Uri.parse("${varray[position].uploadedFile}")))}
//             */
//            //Log.d("TEST", varray[position].uploadedFile.size())
//            holder.itemView.setOnClickListener {
//                var intent = Intent(applicationContext, DownActivity::class.java)
//                intent.putExtra("title", varray[position].title)
//                startActivity(intent)
//            }
//        }

//        override fun getItemCount(): Int {
//            return varray.size
//        }

    }
    override fun onSupportNavigateUp(): Boolean {
        Log.d("actionbar","onSupportNabigateUp")
        val intent=Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }
}