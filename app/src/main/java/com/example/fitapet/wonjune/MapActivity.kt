package com.example.fitapet.wonjune

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import com.example.fitapet.MainActivity
import com.example.fitapet.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    var lat: Double = 0.0
    var long: Double = 0.0
    var retrofit = Retrofit.Builder()
        .baseUrl("http://118.45.212.21:8000")
        //http://118.45.212.21:8000
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 액션바 왼쪽에 버튼 만들기(defalut:뒤로가기버튼)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        supportActionBar?.setTitle("펫시터 위치 확인")
        val fm = supportFragmentManager
        //초기옵션 설정
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(35.8851831, 128.6142943), 12.0))
        val mapFragment = (fm.findFragmentById(R.id.map) as MapFragment?)
            ?: MapFragment.newInstance(options)
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    //권한설정 여부
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.isIndoorEnabled=true
        val uiSettings = naverMap.uiSettings
        uiSettings.isIndoorLevelPickerEnabled=true
        uiSettings.isLocationButtonEnabled=true
        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow


        var traceService = retrofit.create(traceService::class.java)

        naverMap.addOnLocationChangeListener { location ->
            //var lat = location.latitude
            //var long = location.longitude
            var idStr = "museoul@naver.com"
            traceService.requestLoc(idStr).enqueue(object: Callback<traceLoc> {
                override fun onResponse(call: Call<traceLoc>, response: Response<traceLoc>) {
                    var trace = response.body()
                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(trace!!.lat, trace!!.long))
                    naverMap.moveCamera(cameraUpdate)
                    val marker = Marker()
                    marker.position = LatLng(trace!!.lat, trace!!.long)
                    marker.map = naverMap
                }

                override fun onFailure(call: Call<traceLoc>, t: Throwable) {
                    var dialog = AlertDialog.Builder(this@MapActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다." + t.message)
//                    Log.d("호출실패"+t.message)
                    dialog.show()
                }

            })
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        Log.d("actionbar","onSupportNabigateUp")
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
