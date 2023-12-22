package com.dicoding.bottomnavigationbar.data.retrofit

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @field:SerializedName("predicted_class")
    val predictedClass: String? = null
)