package com.dicoding.bottomnavigationbar.ui.daftarRS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.ars.Rs
import com.dicoding.bottomnavigationbar.databinding.ActivityDetailRsBinding
import com.google.android.gms.maps.MapsInitializer

class DetailRsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext)
        binding = ActivityDetailRsBinding.inflate(layoutInflater)
        setContentView( binding.root)

        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.map, mapFragment)
            .commit()

        val receivedData: Rs? = intent.getParcelableExtra("DATA")

        if (receivedData != null) {
            binding.tvNamaRs.text = receivedData.nama
            binding.tvDescRs.text = receivedData.descRS
            binding.tvAlamatRs.text = receivedData.alamatRS
            binding.tvJmlhDokter.text = receivedData.jmlhDokter
            binding.tvJamKerja.text = receivedData.jamBuka
            binding.tvTelp.text = receivedData.kontak

            Glide.with(this)
                .load(receivedData.photo)
                .error(R.drawable.baseline_error_24)
                .into(binding.ivDetailRs)

//            binding.tvTelp.setOnClickListener {
//                val formattedPhoneNumber = replaceLeading0With62(receivedData.nomor)
//                openWhatsApp(formattedPhoneNumber)
//            }
        } else {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}