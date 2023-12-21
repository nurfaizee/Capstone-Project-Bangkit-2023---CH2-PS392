package com.dicoding.bottomnavigationbar.ui.daftarAhliGizi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.bottomnavigationbar.R
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGizi
import com.dicoding.bottomnavigationbar.databinding.ActivityDetailAhliGiziBinding

class DetailAhliGiziActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAhliGiziBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAhliGiziBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val receivedData: AhliGizi? = intent.getParcelableExtra("DATA")

        if (receivedData != null) {
            binding.tvNamaDokter.text = receivedData.nama
            binding.tvRs.text = receivedData.RumahSakit
            binding.btnTelp.text = receivedData.nomor
            binding.btnTelp.text = "Hubungi"
            binding.tvJadwal.text = receivedData.jadwal
            binding.tvPengalaman.text = receivedData.pengalaman

            Glide.with(this)
                .load(receivedData.photo)
                .error(R.drawable.baseline_error_24)
                .into(binding.ivDokter)

            binding.btnTelp.setOnClickListener {
                val formattedPhoneNumber = replaceLeading0With62(receivedData.nomor)
                if (isValidPhoneNumber(formattedPhoneNumber)) {
                    openWhatsApp(formattedPhoneNumber)
                } else {
                    Toast.makeText(this, "Nomor telepon tidak valid", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length >= 9 && phoneNumber.all { it.isDigit() }
    }
    private fun replaceLeading0With62(phoneNumber: String): String {
        return if (phoneNumber.startsWith("0")) {
            "62" + phoneNumber.substring(1)
        } else {
            phoneNumber
        }
    }
    private fun openWhatsApp(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber")
        startActivity(intent)
    }
}