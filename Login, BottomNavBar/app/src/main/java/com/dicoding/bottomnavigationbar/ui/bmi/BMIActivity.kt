package com.dicoding.bottomnavigationbar.ui.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Hitung BMI"

        setupButtonListeners()

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
        val weight = binding.etNumberBerat.text.toString().toFloatOrNull() ?: 0f
        val height = binding.etNumberTinggi.text.toString().toFloatOrNull() ?: 0f

        val bmi = calculateBmiValue(weight, height)
        val bmiCategory = getBmiCategory(bmi)

        val resultText = "BMI: $bmi\nAnda Termasuk Kedalam Kondisi $bmiCategory"

        showResultDialog(resultText, getMessage(bmiCategory))
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
            "Underweight" -> "saran if Underweight"
            "Normal weight" -> "saran if normal Weight"
            "Excess weight" -> "saran if excess weight"
            "Obesity" -> "saran if obesity"
            "Extremely Obese" -> "saran if extremely"
            else -> ""
        }
    }

    private fun showResultDialog(resultText: String, message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_bmi, null)
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
