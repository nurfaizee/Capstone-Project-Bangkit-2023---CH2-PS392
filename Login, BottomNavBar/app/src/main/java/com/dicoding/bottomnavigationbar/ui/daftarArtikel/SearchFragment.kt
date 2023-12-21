package com.dicoding.bottomnavigationbar.ui.daftarArtikel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bottomnavigationbar.data.artikel.Artikel
import com.dicoding.bottomnavigationbar.data.artikel.ArtikelAdapter
import com.dicoding.bottomnavigationbar.data.artikel.DataArtikel
import com.dicoding.bottomnavigationbar.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val list = DataArtikel.artikel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rvArtikel.setHasFixedSize(true)
        showRecyclerList()

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
        return view
    }
    private fun showRecyclerList() {
        binding.rvArtikel.layoutManager = LinearLayoutManager(context)
        val artikelAdapter = ArtikelAdapter(list)
        binding.rvArtikel.adapter = artikelAdapter

        artikelAdapter.setOnItemClickCallback(object : ArtikelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Artikel) {
                val intentToDetail = Intent(requireContext(), DetailArtikelActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun filterList(query: String?) {
        val filteredList = ArrayList<Artikel>()

        for (artikel in list) {
            if (artikel.judulArtikel.contains(query.orEmpty(), true) ||
                artikel.descArtikel.contains(query.orEmpty(), true)
            ) {
                filteredList.add(artikel)
            }
        }

        val artikelAdapter = ArtikelAdapter(filteredList)
        binding.rvArtikel.adapter = artikelAdapter

        artikelAdapter.setOnItemClickCallback(object : ArtikelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Artikel) {
                val intentToDetail = Intent(requireContext(), DetailArtikelActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}