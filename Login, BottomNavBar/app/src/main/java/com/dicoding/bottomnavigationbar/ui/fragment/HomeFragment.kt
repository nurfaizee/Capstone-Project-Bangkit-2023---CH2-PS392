package com.dicoding.bottomnavigationbar.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.bottomnavigationbar.ui.bmi.BMIActivity
import com.dicoding.bottomnavigationbar.databinding.FragmentHomeBinding
import com.dicoding.bottomnavigationbar.ui.daftarAhliGizi.DaftarAhliGizi

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val bmiButton = binding.bmiButton
        val ahligiziButton = binding.ahliGiziButton

        bmiButton.setOnClickListener {
            startBmiActivity()
        }

        ahligiziButton.setOnClickListener {
            startDaftarAhligizi()
        }

        return view
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
}