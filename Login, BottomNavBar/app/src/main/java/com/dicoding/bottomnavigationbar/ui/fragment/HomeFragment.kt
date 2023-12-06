package com.dicoding.bottomnavigationbar.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.dicoding.bottomnavigationbar.BMIActivity
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.ui.DaftarAhliGizi

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the Button by ID
        val bmiButton: Button = view.findViewById(R.id.bmiButton)
        val ahligiziButton: Button = view.findViewById(R.id.ahligiziButton)
        // Set click listener for the button
        bmiButton.setOnClickListener {
            // Call method to start BMI activity
            startBmiActivity()
        }
        ahligiziButton.setOnClickListener {
            // Call method to start BMI activity
            startDaftarAhligizi()
        }

        return view
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