package com.dicoding.bottomnavigationbar.ui.daftarRS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGizi
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGiziAdapter
import com.dicoding.bottomnavigationbar.data.ars.DataRs
import com.dicoding.bottomnavigationbar.data.ars.Rs
import com.dicoding.bottomnavigationbar.data.ars.RsAdapter
import com.dicoding.bottomnavigationbar.databinding.ActivityDaftarRsBinding
import com.dicoding.bottomnavigationbar.ui.daftarAhliGizi.DetailAhliGiziActivity

class DaftarRS : AppCompatActivity() {

    private lateinit var binding : ActivityDaftarRsBinding
    private val list = DataRs.rs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarRsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDaftarRs.setHasFixedSize(true)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvDaftarRs.layoutManager = LinearLayoutManager(this)
        val rsAdapter = RsAdapter(list)
        binding.rvDaftarRs.adapter = rsAdapter

        rsAdapter.setOnItemClickCallback(object : RsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Rs) {
                val intentToDetail = Intent(this@DaftarRS, DetailRsActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }
}