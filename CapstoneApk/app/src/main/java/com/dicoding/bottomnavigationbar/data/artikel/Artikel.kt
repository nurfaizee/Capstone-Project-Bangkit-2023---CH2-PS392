package com.dicoding.bottomnavigationbar.data.artikel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artikel (
    val judulArtikel: String,
    val descArtikel:String,
    val imgArtikel: String
): Parcelable