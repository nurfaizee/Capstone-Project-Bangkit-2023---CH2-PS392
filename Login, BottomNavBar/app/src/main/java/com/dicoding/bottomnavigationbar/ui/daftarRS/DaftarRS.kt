package com.dicoding.bottomnavigationbar.ui.daftarRS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
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

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
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

    private fun filterList(query: String?) {
        val filteredList = ArrayList<Rs>()

        for (rs in list) {
            if (rs.nama.contains(query.orEmpty(), true) ||
                rs.alamatRS.contains(query.orEmpty(), true)
            ) {
                filteredList.add(rs)
            }
        }
        val rsAdapter = RsAdapter(filteredList)
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