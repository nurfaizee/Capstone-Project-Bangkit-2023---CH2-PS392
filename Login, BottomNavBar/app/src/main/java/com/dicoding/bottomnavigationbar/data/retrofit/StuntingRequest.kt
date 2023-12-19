package com.dicoding.bottomnavigationbar.data.retrofit

import com.google.gson.annotations.SerializedName

data class StuntingRequest(

	@field:SerializedName("Jenis_Kelamin")
	val jenisKelamin: String? = null,
	@field:SerializedName("Tinggi_Badan")
	val tinggiBadan: Int? = null,
	@field:SerializedName("Berat_Badan")
	val beratBadan: Int? = null,
	@field:SerializedName("Usia")
	val usia: Int? = null,
)
