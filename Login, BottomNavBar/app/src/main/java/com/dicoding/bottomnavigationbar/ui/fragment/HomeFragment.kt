package com.dicoding.bottomnavigationbar.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.Artikel
import com.dicoding.bottomnavigationbar.data.ArtikelAdapter
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.ui.bmi.BMIActivity
import com.dicoding.bottomnavigationbar.databinding.FragmentHomeBinding
import com.dicoding.bottomnavigationbar.ui.about.AboutActivity
import com.dicoding.bottomnavigationbar.ui.daftarAhliGizi.DaftarAhliGizi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Artikel>()
    private val imageArray:ArrayList<Int> = ArrayList()
    private lateinit var auth : FirebaseAuth
//    private val carouselview:CarouselView?=null
    private val loginManager: LoginManager by lazy { LoginManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth

        val bmiButton = binding.bmiButton
        val ahligiziButton = binding.ahliGiziButton
        val aboutbutton = binding.aboutbutton

//        carouselView = binding.ivCarousel
        imageArray.add(R.drawable.img_iklan_capstone)
        lifecycleScope.launch {
            // Observe username changes
            loginManager.usernameFlow.collect { username ->
                binding?.tv1?.text = username ?: "No username available"
            }
        }

        val currentUser = auth.currentUser
        currentUser?.let {
            binding?.tv1?.text = currentUser.displayName ?: "No username available"
        }


        bmiButton.setOnClickListener {
            startBmiActivity()
        }

        ahligiziButton.setOnClickListener {
            startDaftarAhligizi()
        }
        aboutbutton.setOnClickListener {
            startAbout()
        }
        list.addAll(getList())
        showRecyclerList()
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
    private fun getList(): ArrayList<Artikel> {
        val dataJudul = resources.getStringArray(R.array.judul_artikel)
        val dataDescArtikel = resources.getStringArray(R.array.data_desc_artikel)
        val dataGambarArtikel = resources.getStringArray(R.array.data_gambar_artikel)
        val listArtikel = ArrayList<Artikel>()
        for (i in dataJudul.indices) {
            val artikel = Artikel(dataJudul[i], dataDescArtikel[i], dataGambarArtikel[i])
            listArtikel.add(artikel)
        }
        return listArtikel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun startBmiActivity() {
        val intent = Intent(activity, BMIActivity::class.java)
        startActivity(intent)
    }
    private fun startDaftarAhligizi() {
        val intent = Intent(activity, DaftarAhliGizi::class.java)
        startActivity(intent)
    }
    private fun startAbout() {
        val intent = Intent(activity, AboutActivity::class.java)
        startActivity(intent)
    }
}