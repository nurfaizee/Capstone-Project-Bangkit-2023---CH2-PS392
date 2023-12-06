package com.dicoding.bottomnavigationbar.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.ui.data.AhliGizi
import com.dicoding.bottomnavigationbar.ui.data.ListAhliGiziAdapter
import com.dicoding.bottomnavigationbar.ui.main.MainActivity

class DaftarAhliGizi : AppCompatActivity() {
    private lateinit var rvAhligizi: RecyclerView
    private val list = ArrayList<AhliGizi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_ahli_gizi)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))

        rvAhligizi = findViewById(R.id.rv_ahligizi)
        rvAhligizi.setHasFixedSize(true)
        list.addAll(getList())
        showRecyclerList()
    }
    private fun getList(): ArrayList<AhliGizi> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataRumahSakit = resources.getStringArray(R.array.data_rumahsakit)
        val dataNomor = resources.getStringArray(R.array.data_nomor)
        val dataJadwal = resources.getStringArray(R.array.data_jadwal)
        val dataGambar = resources.getStringArray(R.array.data_gambar)
        val listAhligizi = ArrayList<AhliGizi>()
        for (i in dataName.indices) {
            val ahligizi = AhliGizi(dataName[i], dataRumahSakit[i],dataNomor[i], dataJadwal[i],dataGambar[i])
            listAhligizi.add(ahligizi)
        }
        return listAhligizi
    }
    private fun showRecyclerList() {
        rvAhligizi.layoutManager = LinearLayoutManager(this)
        val listAhliGiziAdapter = ListAhliGiziAdapter(list)
        rvAhligizi.adapter = listAhliGiziAdapter

        listAhliGiziAdapter.setOnItemClickCallback(object : ListAhliGiziAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AhliGizi) {
                val intentToDetail = Intent(this@DaftarAhliGizi, MainActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }
}