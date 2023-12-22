package com.dicoding.bottomnavigationbar.data.ahliGizi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AhliGizi(
    val nama: String,
    val RumahSakit: String,
    val nomor: String,
    val jadwal: String,
    val photo: String,
    val pengalaman: String,
    val about: String
) : Parcelable