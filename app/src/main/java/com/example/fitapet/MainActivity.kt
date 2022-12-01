package com.example.fitapet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fitapet.PetsitterList.CatServiceFragment
import com.example.fitapet.PetsitterList.DogServiceFragment
import com.example.fitapet.databinding.ActivityMainBinding
import com.example.fitapet.navfragment.FriendFragment
import com.example.fitapet.navfragment.HomeFragment
import com.example.fitapet.navfragment.IngFragment
import com.example.fitapet.navfragment.MypageFragment
import com.example.fitapet.ui.animalReg.AnimalRegFragment
import com.example.fitapet.ui.reservation.petList.PetListRecyclerFragment
import com.example.fitapet.wonjune.MyPgActivity

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Log.d("LifeCycleTest","onCreate")
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Log.d("clickTest","homeclick!")
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.ing -> {
//                    loadFragment(IngFragment())
                    val intent = Intent(this, MyPgActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
                }
                R.id.friend -> {
                    Log.d("clickTest","friendclick!")
                    loadFragment(FriendFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.mypage -> {
                    Log.d("clickTest","mypagelick!")
                    loadFragment(MypageFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }
    private fun loadFragment(fragment: Fragment){
        Log.d("clickTest","click!->"+fragment.tag)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

