package com.dicoding.bottomnavigationbar.data.ahliGizi


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bottomnavigationbar.R
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.databinding.ItemAhligiziBinding
import com.dicoding.bottomnavigationbar.databinding.ItemArtikelBinding

class AhliGiziAdapter(private val listAhliGizi: List<AhliGizi>) : RecyclerView.Adapter<AhliGiziAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: ItemAhligiziBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ahliGizi: AhliGizi) {
            binding.tvNamaDokter.text = ahliGizi.nama
            binding.tvRs.text = ahliGizi.RumahSakit
            binding.tvTelp.text = ahliGizi.nomor
            binding.tvJadwal.text = ahliGizi.jadwal
            Glide.with(itemView.context)
                .load(ahliGizi.photo)
                .into(binding.ivDokter)

            itemView.setOnClickListener { onItemClickCallback.onItemClicked(ahliGizi) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemAhligiziBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listAhliGizi.size
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val ahliGizi = listAhliGizi[position]
        holder.bind(ahliGizi)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: AhliGizi)
    }

}