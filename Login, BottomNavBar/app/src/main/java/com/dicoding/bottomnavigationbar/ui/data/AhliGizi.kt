package com.dicoding.bottomnavigationbar.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AhliGizi(
    val nama: String,
    val RumahSakit: String,
    val nomor: String,
    val jadwal: String,
    val photo: String
) : Parcelable