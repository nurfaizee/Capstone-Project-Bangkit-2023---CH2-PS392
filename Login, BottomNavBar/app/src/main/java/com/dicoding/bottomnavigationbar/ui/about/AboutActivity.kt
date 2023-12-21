package com.dicoding.bottomnavigationbar.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityAboutBinding
import com.dicoding.bottomnavigationbar.databinding.ActivityDetailRsBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView( binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }
}