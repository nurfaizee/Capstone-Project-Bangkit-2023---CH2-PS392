package com.dicoding.bottomnavigationbar.ui.bmi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityBmiBinding

@SuppressLint("SetTextI18n")
class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
        
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setupButtonListeners() {
        binding.btnSelanjutnya.setOnClickListener { calculateBmi() }
        binding.btnIncreaseBerat.setOnClickListener { increaseWeight() }
        binding.btnDecreaseBerat.setOnClickListener { decreaseWeight() }
        binding.btnIncreaseTinggi.setOnClickListener { increaseHeight() }
        binding.btnDecreaseTinggi.setOnClickListener { decreaseHeight() }
    }

    private fun increaseWeight() {
        val currentWeight = binding.etNumberBerat.text.toString().toFloatOrNull() ?: 0f
        binding.etNumberBerat.setText((currentWeight + 1).toString())
    }

    private fun decreaseWeight() {
        val currentWeight = binding.etNumberBerat.text.toString().toFloatOrNull() ?: 0f
        if (currentWeight > 0) {
            binding.etNumberBerat.setText((currentWeight - 1).toString())
        }
    }

    private fun increaseHeight() {
        val currentHeight = binding.etNumberTinggi.text.toString().toFloatOrNull() ?: 0f
        binding.etNumberTinggi.setText((currentHeight + 1).toString())
    }

    private fun decreaseHeight() {
        val currentHeight = binding.etNumberTinggi.text.toString().toFloatOrNull() ?: 0f
        if (currentHeight > 0) {
            binding.etNumberTinggi.setText((currentHeight - 1).toString())
        }
    }


    private fun calculateBmi() {
        val weightStr = binding.etNumberBerat.text.toString()
        val heightStr = binding.etNumberTinggi.text.toString()

        if (weightStr.isEmpty() || weightStr.toFloatOrNull() == 0.0f || heightStr.isEmpty() || heightStr.toFloatOrNull() == 0.0f) {
            Toast.makeText(this, "Lengkapi Data", Toast.LENGTH_SHORT).show()
            return
        } else{
            val weight = weightStr.toFloatOrNull() ?: 0f
            val height = heightStr.toFloatOrNull() ?: 0f

            Log.d("SendData", "  detectBMI")
            Log.d("SendData", "  Tinggi Badan: $height")
            Log.d("SendData", "  Berat Badan: $weight")
            val bmi = calculateBmiValue(weight, height)
            val bmiCategory = getBmiCategory(bmi)

            val resultText = "BMI: $bmi\nAnda Termasuk Kedalam Kondisi $bmiCategory"

            showResultDialog(resultText, getMessage(bmiCategory))
        }
    }

    private fun calculateBmiValue(weight: Float, height: Float): Float {
        return weight / (height/100 * height/100)
    }

    private fun getBmiCategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Excess weight"
            bmi < 30 -> "Obesity"
            else -> "Extremely Obese"
        }
    }

    private fun getMessage(category: String): String {
        return when (category) {
            "Underweight" -> "Perbanyak asupan protein dan lemak untuk meningkatkan berat badan"
            "Normal weight" -> "Pertahankan asupan gizi untuk memenuhi tumbuh kembang yang baik"
            "Excess weight" -> "Kurangi asupan lemak dan perbanyak asupan serat hijau"
            "Obesity" -> "Kurangi asupan lemak si kecil dan jalani atur diet secara berkala"
            "Extremely Obese" -> "Kurangi dan kontrol asupan lemak dan karbohidrat dan silahkan kunjungi rumah sakit terdekat"
            else -> ""
        }
    }

    private fun showResultDialog(resultText: String, message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_, null)
        val tvDialogTitle = dialogView.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)
        val btnDialogOk = dialogView.findViewById<Button>(R.id.btnDialogOk)

        tvDialogTitle.text = "BMI Result"
        tvDialogMessage.text = "$resultText\n\n$message"

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = builder.create()

        btnDialogOk.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
