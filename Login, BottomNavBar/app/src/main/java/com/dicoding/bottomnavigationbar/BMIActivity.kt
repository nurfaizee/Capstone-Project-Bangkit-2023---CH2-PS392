package com.dicoding.bottomnavigationbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class BMIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        val buttonCalculateBMI: Button = findViewById(R.id.buttonCalculateBMI)
        val editTextWeight: EditText = findViewById(R.id.editTextWeight)
        val editTextHeight: EditText = findViewById(R.id.editTextHeight)
        val textViewResult: TextView = findViewById(R.id.textViewResult)

        buttonCalculateBMI.setOnClickListener {
            val weightStr = editTextWeight.text.toString()
            val heightStr = editTextHeight.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                val weight = weightStr.toFloat()
                val height = heightStr.toFloat()

                val bmi = calculateBMI(weight, height/100)
                val category = getCategory(bmi)
                textViewResult.text = "$category"
            } else {
                textViewResult.text = "Please enter both weight and height."
            }
        }
    }

    private fun calculateBMI(weight: Float, height: Float): Float {
        return weight / (height * height)
    }
    private fun getCategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal Weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }
    }
}
