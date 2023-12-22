package com.dicoding.bottomnavigationbar.ui.stunting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.retrofit.Retro
import com.dicoding.bottomnavigationbar.data.retrofit.StuntingRequest
import com.dicoding.bottomnavigationbar.data.retrofit.StuntingResponse
import com.dicoding.bottomnavigationbar.data.retrofit.UserApi
import com.dicoding.bottomnavigationbar.databinding.ActivityStuntingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class StuntingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuntingBinding
    private var selectedGender: String = ""
    private lateinit var viewModel: StuntingViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[StuntingViewModel::class.java]
        binding = ActivityStuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Deteksi Risiko Stunting"
        viewModel.stuntingResponse.observe(this) { response ->
            response?.let {
                val resultText = "Prediction: $it"
                Log.d("StuntingActivity", "Response: $response")

                showResultDialog(resultText, getMessage("aman"))
            }
        }
        setupButtonListeners()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        progressBar = binding.progressBar

    }

    private fun setupButtonListeners() {
        binding.btnSelanjutnya.setOnClickListener { detectStunting() }
        binding.btnIncreaseBerat.setOnClickListener { increaseWeight() }
        binding.btnDecreaseBerat.setOnClickListener { decreaseWeight() }
        binding.btnIncreaseTinggi.setOnClickListener { increaseHeight() }
        binding.btnDecreaseTinggi.setOnClickListener { decreaseHeight() }
        binding.btnIncreaseUmur.setOnClickListener { increaseAge() }
        binding.btnDecreaseUmur.setOnClickListener { decreaseAge() }

        binding.btnLakilaki.setOnClickListener {
            selectedGender = "Laki-laki"
            updateGenderButtons()
        }
        binding.btnPerempuan.setOnClickListener {
            selectedGender = "Perempuan"
            updateGenderButtons()
        }
    }

    private fun updateGenderButtons() {
        val lakilakiColor = if (selectedGender == "Laki-laki") R.color.blue else R.color.blue_light
        val perempuanColor = if (selectedGender == "Perempuan") R.color.blue else R.color.blue_light

        binding.btnLakilaki.setBackgroundColor(ContextCompat.getColor(this, lakilakiColor))
        binding.btnPerempuan.setBackgroundColor(ContextCompat.getColor(this, perempuanColor))
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
        if (isDataValid()) {
            progressBar.visibility = View.VISIBLE

            val weight = binding.etBerat.text.toString().toIntOrNull() ?: 0
            val height = binding.etTinggibadan.text.toString().toIntOrNull() ?: 0
            val age = binding.etUmur.text.toString().toIntOrNull() ?: 0
            val gender = selectedGender

            Log.d("SendData", "  detectStunting")
            Log.d("SendData", "  Jenis Kelamin: $gender")
            Log.d("SendData", "  Tinggi Badan: $height")
            Log.d("SendData", "  Berat Badan: $weight")
            Log.d("SendData", "  Usia: $age")

            sendData(selectedGender, height, weight, age)
        } else showToast("Lengkapi Data")
    }
    private fun isDataValid(): Boolean {
        return (
                binding.etBerat.text.isNotEmpty() &&
                        binding.etTinggibadan.text.isNotEmpty() &&
                        binding.etUmur.text.isNotEmpty() &&
                        selectedGender.isNotEmpty()
                )
    }

    private fun sendData(jenisKelamin: String, tinggiBadan: Int, beratBadan: Int, usia: Int) {
        val retro = Retro().getRetroClientInstanceStunting().create(UserApi::class.java)

        Log.d("SendData", "Sending request to deteksiStunting:")
        Log.d("SendData", "  Jenis Kelamin: $jenisKelamin")
        Log.d("SendData", "  Tinggi Badan: $tinggiBadan")
        Log.d("SendData", "  Berat Badan: $beratBadan")
        Log.d("SendData", "  Usia: $usia")
        val request = StuntingRequest(jenisKelamin, tinggiBadan, beratBadan, usia)

        retro.deteksiStunting(request)

        retro.deteksiStunting(request).enqueue(object : Callback<StuntingResponse> {
            override fun onResponse(call: Call<StuntingResponse>, response: Response<StuntingResponse>) {
                progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val prediksi = responseData?.prediction
                    val hasil = kategori("$prediksi", selectedGender)
                    if (responseData != null) {
                        Log.d("SendData", "  kode: $response")
                        Log.d("SendData", " $responseData")

                        showResultDialog(hasil, getMessage("$prediksi"))
                    }
                } else {
                    response.code()
                    response.message()
                }
            }

            override fun onFailure(call: Call<StuntingResponse>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
    private fun getMessage(category: String): String {
        return when (category) {
            "Tidak_Berisiko" -> "1. Tetapkan pola makan yang sehat dan seimbang.\n" +
                    "2. Ajarkan gaya hidup aktif dan bergerak.\n" +
                    "3. Perhatikan pertumbuhan dan perkembangan anak secara berkala.\n" +
                    "4. Lakukan pemeriksaan kesehatan rutin."
            "Beresiko_Ringan" -> "1. Tingkatkan variasi dalam pola makan anak dengan memasukkan berbagai jenis makanan sehat.\n" +
                    "2. Pastikan anak mendapatkan asupan nutrisi yang mencukupi untuk pertumbuhan dan perkembangannya.\n" +
                    "3. Ajarkan kebiasaan hidup sehat, seperti olahraga teratur dan kebersihan diri.\n" +
                    "4. Konsultasikan dengan dokter atau ahli gizi untuk saran lebih lanjut."
            "Beresiko_Sedang" -> "1. Segera konsultasikan dengan dokter atau ahli gizi untuk penilaian lebih lanjut.\n" +
                    "2. Ikuti saran dan rekomendasi yang diberikan oleh profesional kesehatan.\n" +
                    "3. Pantau pola makan dan aktivitas anak secara ketat."
            "Beresiko_Tinggi" -> "1. Segera konsultasikan dengan dokter atau spesialis gizi anak.\n" +
                    "2. Ikuti rencana perawatan yang direkomendasikan.\n" +
                    "3. Perluasan pemeriksaan kesehatan dan pemantauan secara teratur.\n" +
                    "4. Libatkan dukungan dari keluarga dan lingkungan sekitar untuk mendukung perubahan gaya hidup dan pola makan anak.\n" +
                    "5. Penting untuk dicatat bahwa saran ini bersifat umum, dan setiap anak mungkin memerlukan pendekatan yang disesuaikan dengan kebutuhan dan kondisi kesehatannya. Konsultasikan selalu dengan profesional kesehatan untuk panduan yang lebih spesifik."

            else -> ""

        }
    }
    private fun kategori(category: String, gender: String): String {
        val genderMessage = if (gender == "Perempuan") {
            "Anak perempuan anda "
        } else {
            "Anak laki-laki anda "
        }
        return when (category) {
            "Tidak_Berisiko" -> "$genderMessage Tidak Berisiko"
            "Beresiko_Ringan" -> "$genderMessage Beresiko Ringan"
            "Beresiko_Sedang" -> "$genderMessage Beresiko Sedang"
            "Beresiko_Tinggi" -> "$genderMessage Beresiko Tinggi"

            else -> ""
        }
    }
    private fun showResultDialog(resultText: String, message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_, null)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}