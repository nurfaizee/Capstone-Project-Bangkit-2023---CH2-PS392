package com.dicoding.bottomnavigationbar.data.ars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rs(
    val nama: String,
    val descRS: String,
    val alamatRS: String,
    val jmlhDokter: String,
    val jamBuka: String,
    val kontak: String,
    val photo: String,
    val lan: String,
    val lat: String
) : Parcelable
