package com.dicoding.bottomnavigationbar.ui.about

import android.content.Intent
import android.net.Uri
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

        binding.circleImageView3.setOnClickListener {
            startFeedback()
        }

        binding.circleImageView4.setOnClickListener {
            startPartnership()
        }
    }

    private fun startFeedback() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://docs.google.com/forms/d/1w8AcDqqpeqX9seG0it22Nbo6u1AIQc6_hJGzUpZt1CU/edit")
        startActivity(intent)
    }
    private fun startPartnership() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/+6281216510089")
        startActivity(intent)
    }
}