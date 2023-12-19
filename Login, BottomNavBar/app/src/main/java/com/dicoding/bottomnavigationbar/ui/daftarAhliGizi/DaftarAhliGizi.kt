package com.dicoding.bottomnavigationbar.ui.daftarAhliGizi

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGizi
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGiziAdapter
import com.dicoding.bottomnavigationbar.data.ahliGizi.DataAhliGizi

class DaftarAhliGizi : AppCompatActivity() {
    private lateinit var rvAhligizi: RecyclerView
    private val list = DataAhliGizi.ahliGizi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_ahli_gizi)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))

        rvAhligizi = findViewById(R.id.rv_ahligizi)
        rvAhligizi.setHasFixedSize(true)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvAhligizi.layoutManager = LinearLayoutManager(this)
        val ahliGiziAdapter = AhliGiziAdapter(list)
        rvAhligizi.adapter = ahliGiziAdapter

        ahliGiziAdapter.setOnItemClickCallback(object : AhliGiziAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AhliGizi) {
                val intentToDetail = Intent(this@DaftarAhliGizi, DetailAhliGiziActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

}