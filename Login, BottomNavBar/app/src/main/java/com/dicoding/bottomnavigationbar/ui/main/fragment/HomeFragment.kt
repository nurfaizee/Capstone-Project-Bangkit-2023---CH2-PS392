package com.dicoding.bottomnavigationbar.ui.main.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.artikel.Artikel
import com.dicoding.bottomnavigationbar.data.artikel.ArtikelAdapter
import com.dicoding.bottomnavigationbar.data.LoginManager
import com.dicoding.bottomnavigationbar.data.artikel.DataArtikel
import com.dicoding.bottomnavigationbar.ui.bmi.BMIActivity
import com.dicoding.bottomnavigationbar.databinding.FragmentHomeBinding
import com.dicoding.bottomnavigationbar.ui.about.AboutActivity
import com.dicoding.bottomnavigationbar.ui.daftarAhliGizi.DaftarAhliGizi
import com.dicoding.bottomnavigationbar.ui.main.fragment.daftarArtikel.DetailArtikelActivity
import com.dicoding.bottomnavigationbar.ui.daftarRS.DaftarRS
import com.dicoding.bottomnavigationbar.ui.gizi.GiziActivity
import com.dicoding.bottomnavigationbar.ui.stunting.StuntingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private val list = DataArtikel.artikel
    private val imageArray:ArrayList<Int> = ArrayList()
    private val loginManager: LoginManager by lazy { LoginManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val view = binding.root

        val stuntingbutton = binding.stuntingButton
        val ahligiziButton = binding.ahliGiziButton
        val bmiButton = binding.bmiButton
        val cariAhliGizi = binding.cariAhliGizi
        val aboutbutton = binding.aboutbutton
        val deteksiGizi = binding.deteksiGiziButton
        val event = binding.ivCarousel
        event.setOnClickListener {
            startEvent()
        }

        imageArray.add(R.drawable.img_iklan_capstone)
        lifecycleScope.launch {
            loginManager.usernameFlow.collect { username ->
                binding.tv1.text = username ?: "No username available"
            }
        }
        val currentUser = auth.currentUser

        currentUser?.let {
            binding.tv1.text = currentUser.displayName ?: "No username available"
        }
        stuntingbutton.setOnClickListener {
            startStuntingActivity()
        }
        ahligiziButton.setOnClickListener {
            startDaftarAhligizi()
        }
        bmiButton.setOnClickListener {
            startBmiActivity()
        }
        cariAhliGizi.setOnClickListener {
            startRsActivity()
        }
        aboutbutton.setOnClickListener {
            startAbout()
        }
        deteksiGizi.setOnClickListener {
            startGiziActivity()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun startStuntingActivity() {
        val intent = Intent(activity, StuntingActivity::class.java)
        startActivity(intent)
    }
    private fun startDaftarAhligizi() {
        val intent = Intent(activity, DaftarAhliGizi::class.java)
        startActivity(intent)
    }
    private fun startBmiActivity() {
        val intent = Intent(activity, BMIActivity::class.java)
        startActivity(intent)
    }
    private fun startRsActivity() {
        val intent = Intent(activity,DaftarRS::class.java)
        startActivity(intent)
    }
    private fun startAbout() {
        val intent = Intent(activity, AboutActivity::class.java)
        startActivity(intent)
    }
    private fun startGiziActivity() {
        val intent = Intent(activity, GiziActivity::class.java)
        startActivity(intent)
    }

    private fun startEvent() {
        val websiteUri = Uri.parse("https://docs.google.com/forms/d/1ao24C5zW97MEty3F5V0gADBpjHmSB06guL5kmplzOr0/edit")

        val intent = Intent(Intent.ACTION_VIEW)

        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            intent.data = Uri.parse(websiteUri.toString())
            startActivity(intent)

        } else {
            Toast.makeText(activity, "Tidak ada aplikasi yang dapat menangani intent ini.", Toast.LENGTH_SHORT).show()
        }
    }
}