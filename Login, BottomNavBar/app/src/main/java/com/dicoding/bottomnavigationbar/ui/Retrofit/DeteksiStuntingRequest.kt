package com.dicoding.bottomnavigationbar.ui.Retrofit

import com.google.gson.annotations.SerializedName

data class DeteksiStuntingRequest(
    @field:SerializedName("Jenis_Kelamin")
    val jenisKelamin: String?,
    @field:SerializedName("Tinggi_Badan")
    val tinggiBadan: Int?,
    @field:SerializedName("Berat_Badan")
    val beratBadan: Int?,
    @field:SerializedName("Usia")
    val usia: Int?
)