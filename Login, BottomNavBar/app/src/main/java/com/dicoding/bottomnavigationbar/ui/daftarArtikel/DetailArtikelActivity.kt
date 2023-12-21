package com.dicoding.bottomnavigationbar.ui.daftarArtikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.data.artikel.Artikel
import com.dicoding.bottomnavigationbar.databinding.ActivityDetailArtikelBinding

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val artikel = intent.getParcelableExtra<Artikel>("DATA")

        if (artikel != null) {

            binding.tvJudulArtikel.text = artikel.judulArtikel
            binding.tvDeskripsiArtikel.text = artikel.descArtikel
            Glide.with(this).load(artikel.imgArtikel).into(binding.ivArtikel)
        } else {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
