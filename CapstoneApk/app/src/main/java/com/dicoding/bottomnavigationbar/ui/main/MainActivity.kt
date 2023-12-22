 package com.dicoding.bottomnavigationbar.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityMainBinding
import com.dicoding.bottomnavigationbar.ui.main.fragment.HomeFragment
import com.dicoding.bottomnavigationbar.ui.main.fragment.daftarArtikel.SearchFragment
import com.dicoding.bottomnavigationbar.ui.main.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

 class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
     private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else->false
            }
        }
        replaceFragment(HomeFragment())
    }

     private fun replaceFragment(fragment: Fragment){
         supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
     }
}