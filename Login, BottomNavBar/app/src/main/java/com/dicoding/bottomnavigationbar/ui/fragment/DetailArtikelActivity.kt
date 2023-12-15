package com.dicoding.bottomnavigationbar.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.Artikel
import com.dicoding.bottomnavigationbar.databinding.ActivityDetailArtikelBinding

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val artikel = intent.getParcelableExtra<Artikel>("DATA")

        if (artikel != null) {

            binding.tvJudulArtikel.text = artikel.judulArtikel
            binding.tvDeskripsiArtikel.text = artikel.descArtikel
            Glide.with(this).load(artikel.imgArtikel).into(binding.ivArtikel)
        }
    }
}
