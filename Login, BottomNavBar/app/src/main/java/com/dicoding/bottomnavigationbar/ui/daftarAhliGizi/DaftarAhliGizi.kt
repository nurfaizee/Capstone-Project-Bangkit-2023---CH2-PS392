package com.dicoding.bottomnavigationbar.ui.daftarAhliGizi

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGizi
import com.dicoding.bottomnavigationbar.data.ahliGizi.AhliGiziAdapter
import com.dicoding.bottomnavigationbar.data.ahliGizi.DataAhliGizi
import com.dicoding.bottomnavigationbar.data.artikel.Artikel
import com.dicoding.bottomnavigationbar.data.artikel.ArtikelAdapter
import com.dicoding.bottomnavigationbar.databinding.ActivityDaftarAhliGiziBinding
import com.dicoding.bottomnavigationbar.ui.daftarArtikel.DetailArtikelActivity

class DaftarAhliGizi : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarAhliGiziBinding
    private val list = DataAhliGizi.ahliGizi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarAhliGiziBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))

        binding.rvAhligizi.setHasFixedSize(true)
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
        binding.rvAhligizi.layoutManager = LinearLayoutManager(this)
        val ahliGiziAdapter = AhliGiziAdapter(list)
        binding.rvAhligizi.adapter = ahliGiziAdapter

        ahliGiziAdapter.setOnItemClickCallback(object : AhliGiziAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AhliGizi) {
                val intentToDetail = Intent(this@DaftarAhliGizi, DetailAhliGiziActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun filterList(query: String?) {
        val filteredList = ArrayList<AhliGizi>()

        for (ahliGizi in list) {
            if (ahliGizi.nama.contains(query.orEmpty(), true) ||
                ahliGizi.RumahSakit.contains(query.orEmpty(), true)
            ) {
                filteredList.add(ahliGizi)
            }
        }
        val ahliGiziAdapter = AhliGiziAdapter(filteredList)
        binding.rvAhligizi.adapter = ahliGiziAdapter

        ahliGiziAdapter.setOnItemClickCallback(object : AhliGiziAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AhliGizi) {
                val intentToDetail = Intent(this@DaftarAhliGizi, DetailAhliGiziActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }
}
