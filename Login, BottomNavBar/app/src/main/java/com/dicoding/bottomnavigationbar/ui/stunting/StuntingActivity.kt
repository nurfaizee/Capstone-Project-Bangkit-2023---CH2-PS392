package com.dicoding.bottomnavigationbar.ui.stunting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.databinding.ActivityStuntingBinding
import com.dicoding.bottomnavigationbar.ui.Retrofit.Retro
import com.dicoding.bottomnavigationbar.ui.Retrofit.StuntingRequest
import com.dicoding.bottomnavigationbar.ui.Retrofit.StuntingResponse
import com.dicoding.bottomnavigationbar.ui.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StuntingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuntingBinding
    private var selectedGender: String = ""
    private lateinit var viewModel: StuntingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StuntingViewModel::class.java)
        binding = ActivityStuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Deteksi Risiko Stunting"
        viewModel.stuntingResponse.observe(this, Observer { response ->
            response?.let {
                val resultText = "Prediction: $it"
                Log.d("StuntingActivity", "Response: $response")

                // Call a function to show the result in your UI
                showResultDialog(resultText, getMessage("aman"))
            }
        })
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.btnSelanjutnya.setOnClickListener { detectStunting() }
        binding.btnIncreaseBerat.setOnClickListener { increaseWeight() }
        binding.btnDecreaseBerat.setOnClickListener { decreaseWeight() }
        binding.btnIncreaseTinggi.setOnClickListener { increaseHeight() }
        binding.btnDecreaseTinggi.setOnClickListener { decreaseHeight() }
        binding.btnIncreaseUmur.setOnClickListener { increaseAge() }
        binding.btnDecreaseUmur.setOnClickListener { decreaseAge() }
        binding.btnLakilaki.setOnClickListener { lakilaki() }
        binding.btnPerempuan.setOnClickListener { perempuan() }
    }

    private fun lakilaki() {
        selectedGender = "Laki-laki"
        binding.btnLakilaki.text = selectedGender
    }

    private fun perempuan() {
        selectedGender = "Perempuan"
        binding.btnPerempuan.text = selectedGender
    }

    private fun increaseWeight() {
        val currentWeight = binding.etBerat.text.toString().toIntOrNull() ?: 0
        binding.etBerat.setText((currentWeight + 1).toString())
    }

    private fun decreaseWeight() {
        val currentWeight = binding.etBerat.text.toString().toIntOrNull() ?: 0
        if (currentWeight > 0) {
            binding.etBerat.setText((currentWeight - 1).toString())
        }
    }
    private fun increaseHeight() {
        val currentHeight = binding.etTinggibadan.text.toString().toIntOrNull() ?: 0
        binding.etTinggibadan.setText((currentHeight + 1).toString())
    }

    private fun decreaseHeight() {
        val currentHeight = binding.etTinggibadan.text.toString().toIntOrNull() ?: 0
        if (currentHeight > 0) {
            binding.etTinggibadan.setText((currentHeight - 1).toString())
        }
    }
    private fun increaseAge() {
        val currentAge = binding.etUmur.text.toString().toIntOrNull() ?: 0
        binding.etUmur.setText((currentAge + 1).toString())
    }

    private fun decreaseAge() {
        val currentAge = binding.etUmur.text.toString().toIntOrNull() ?: 0
        if (currentAge > 0) {
            binding.etUmur.setText((currentAge - 1).toString())
        }
    }

    private fun detectStunting() {
        val weight = binding.etBerat.text.toString().toIntOrNull() ?: 0
        val height = binding.etTinggibadan.text.toString().toIntOrNull() ?: 0
        val age = binding.etUmur.text.toString().toIntOrNull() ?: 0
        val gender = selectedGender
        Log.d("SendData", "  detectStunting")
        Log.d("SendData", "  Jenis Kelamin: $gender")
        Log.d("SendData", "  Tinggi Badan: $height")
        Log.d("SendData", "  Berat Badan: $weight")
        Log.d("SendData", "  Usia: $age")
        sendData( selectedGender, height,weight,  age)
    }
    private fun sendData(jenisKelamin: String, tinggiBadan: Int, beratBadan: Int, usia: Int) {
        val retro = Retro().getRetroClientInstanceStunting().create(UserApi::class.java)

        // Log request information
        Log.d("SendData", "Sending request to deteksiStunting:")
        Log.d("SendData", "  Jenis Kelamin: $jenisKelamin")
        Log.d("SendData", "  Tinggi Badan: $tinggiBadan")
        Log.d("SendData", "  Berat Badan: $beratBadan")
        Log.d("SendData", "  Usia: $usia")
        val request= StuntingRequest( jenisKelamin,tinggiBadan,beratBadan,  usia)
        // Execute the network request asynchronously
        val call = retro.deteksiStunting(request)

        // Execute the network request asynchronously
        retro.deteksiStunting(request).enqueue(object : Callback<StuntingResponse> {
            override fun onResponse(call: Call<StuntingResponse>, response: Response<StuntingResponse>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val responseData = response.body()
                    val prediksi = responseData?.prediction
                    val hasil= kategori("$prediksi")
                    if (responseData != null) {
                        // Data terkirim dengan sukses, lakukan sesuatu dengan respons
                        val resultText = "Predictions: ${prediksi}"
                        Log.d("SendData", "  kode: $response")
                        Log.d("SendData", " $responseData")

                        showResultDialog(hasil, getMessage("$prediksi"))
                    } else {
                        // Tangani jika data yang diharapkan null
                    }
                } else {

                    val errorCode = response.code()
                    val errorMessage = response.message()
                    // Lakukan sesuatu dengan informasi kesalahan
                }
            }

            override fun onFailure(call: Call<StuntingResponse>, t: Throwable) {
                // Handle network request failure
                // t.printStackTrace() jika Anda ingin mencetak detail kesalahan
            }
        })
    }
    private fun getMessage(category: String): String {
        return when (category) {
            "Tidak_Berisiko" -> "sPerbanyak asupan protein dan lemak untuk meningkatkan berat badan sikecil"
            "Beresiko_Ringan" -> "Pertahankan asupan gizi sikecil untuk memenuhi tumbuh kembang yang baik"
            "Beresiko_Sedang" -> "Kurangi asupan lemak sikecil dan perbanyak asupan serat hijau"
            "Beresiko_Tinggi" -> "kurangi asupan lemak si kecil dan jalani atur diet secara berkala"

            else -> ""
        }
    }
    private fun kategori(category: String): String {
        return when (category) {
            "Tidak_Berisiko" -> "Tidak Berisiko"
            "Beresiko_Ringan" -> "Beresiko Ringan"
            "Beresiko_Sedang" -> "Beresiko Sedang"
            "Beresiko_Tinggi" -> "Beresiko Tinggi"

            else -> ""
        }
    }
    private fun showResultDialog(resultText: String, message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_bmi, null)
        val tvDialogTitle = dialogView.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)
        val btnDialogOk = dialogView.findViewById<Button>(R.id.btnDialogOk)

        tvDialogTitle.text = "Stunting Result"
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