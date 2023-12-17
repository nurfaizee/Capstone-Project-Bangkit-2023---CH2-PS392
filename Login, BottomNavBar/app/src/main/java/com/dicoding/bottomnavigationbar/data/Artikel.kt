package com.dicoding.bottomnavigationbar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artikel (
    val judulArtikel: String,
    val descArtikel:String,
    val imgArtikel: String
): Parcelable