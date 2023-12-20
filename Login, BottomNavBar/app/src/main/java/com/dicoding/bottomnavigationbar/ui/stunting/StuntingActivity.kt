package com.dicoding.bottomnavigationbar.ui.stunting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bottomnavigationbar.R
import com.dicoding.bottomnavigationbar.data.retrofit.Retro
import com.dicoding.bottomnavigationbar.data.retrofit.StuntingRequest
import com.dicoding.bottomnavigationbar.data.retrofit.StuntingResponse
import com.dicoding.bottomnavigationbar.data.retrofit.UserApi
import com.dicoding.bottomnavigationbar.databinding.ActivityStuntingBinding
import com.dicoding.bottomnavigationbar.ui.login.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("SetTextI18n")
class StuntingActivity : BaseActivity() {
    private lateinit var binding: ActivityStuntingBinding
    private var selectedGender: String = ""
    private lateinit var viewModel: StuntingViewModel
//    private var userData: UserData? = null

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
    }

    private fun setupButtonListeners() {
        binding.btnSelanjutnya.setOnClickListener { detectStunting() }
        binding.btnIncreaseBerat.setOnClickListener { increaseWeight() }
        binding.btnDecreaseBerat.setOnClickListener { decreaseWeight() }
        binding.btnIncreaseTinggi.setOnClickListener { increaseHeight() }
        binding.btnDecreaseTinggi.setOnClickListener { decreaseHeight() }
        binding.btnIncreaseUmur.setOnClickListener { increaseAge() }
        binding.btnDecreaseUmur.setOnClickListener { decreaseAge() }
        // Listener untuk btnLakilaki
        binding.btnLakilaki.setOnClickListener {
            selectedGender = "Laki-laki"
            updateGenderButtons()
        }

        // Listener untuk btnPerempuan
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
            showProgressBar()

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
        } else showToast(this, "Lengkapi Data")
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

        // Log request information
        Log.d("SendData", "Sending request to deteksiStunting:")
        Log.d("SendData", "  Jenis Kelamin: $jenisKelamin")
        Log.d("SendData", "  Tinggi Badan: $tinggiBadan")
        Log.d("SendData", "  Berat Badan: $beratBadan")
        Log.d("SendData", "  Usia: $usia")
        val request = StuntingRequest(jenisKelamin, tinggiBadan, beratBadan, usia)

        // Execute the network request asynchronously
        val call = retro.deteksiStunting(request)

        // Execute the network request asynchronously
        retro.deteksiStunting(request).enqueue(object : Callback<StuntingResponse> {
            override fun onResponse(call: Call<StuntingResponse>, response: Response<StuntingResponse>) {
                hideProgressBar()
                if (response.isSuccessful) {
                    // Handle successful response
                    val responseData = response.body()
                    val prediksi = responseData?.prediction
                    val hasil = kategori("$prediksi", selectedGender)
                    if (responseData != null) {
                        // Data terkirim dengan sukses, lakukan sesuatu dengan respons
                        val resultText = "Predictions: $prediksi"
                        Log.d("SendData", "  kode: $response")
                        Log.d("SendData", " $responseData")

                        // Simpan data ke dalam database
//                        userData.let { userData ->
//                            userData?.berat = beratBadan
//                            userData?.tinggi = tinggiBadan
//                            userData?.umur = usia
//                            userData?.gender = jenisKelamin
//                            userData?.hasil = prediksi
//
//                        }
//                        saveToDatabase()
//                        showToasts(getString(R.string.added))

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
                hideProgressBar()
                // Handle network request failure
                // t.printStackTrace() jika Anda ingin mencetak detail kesalahan
            }
        })
    }

    // Fungsi untuk menyimpan data ke dalam database
//    private fun saveToDatabase() {
//        userData.let { userData->
//            userData?.tanggal= DateHelper.getCurrentDate()
//        }
//        viewModel.insert(userData as UserData)
//    }
    private fun getMessage(category: String): String {
        return when (category) {
            "Tidak_Berisiko" -> "Perbanyak asupan protein dan lemak untuk meningkatkan berat badan sikecil"
            "Beresiko_Ringan" -> "Pertahankan asupan gizi sikecil untuk memenuhi tumbuh kembang yang baik"
            "Beresiko_Sedang" -> "Kurangi asupan lemak sikecil dan perbanyak asupan serat hijau"
            "Beresiko_Tinggi" -> "kurangi asupan lemak si kecil dan jalani atur diet secara berkala"

            else -> ""
        }
    }
    private fun kategori(category: String, gender: String): String {
        val genderMessage = if (gender == "Perempuan") {
            "Anak perempuan Anda "
        } else {
            "Anak laki-laki Anda "
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
    private fun showToasts(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}