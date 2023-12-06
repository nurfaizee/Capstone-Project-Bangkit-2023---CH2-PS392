package com.dicoding.bottomnavigationbar.ui.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bottomnavigationbar.R
import com.bumptech.glide.Glide

class ListAhliGiziAdapter(private val listAhliGizi: ArrayList<AhliGizi>) : RecyclerView.Adapter<ListAhliGiziAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_ahligizi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,rumahsakit,nomor,jadwal,photo) = listAhliGizi[position]
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .into(holder.imgPhoto) // imageView mana yang akan diterapkan
        holder.tvName.text = name
        holder.tvRumahSakit.text = rumahsakit
        holder.tvNomor.text = nomor
        holder.tvJadwal.text = jadwal
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listAhliGizi[holder.adapterPosition]) }    }
    override fun getItemCount(): Int = listAhliGizi.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvRumahSakit: TextView = itemView.findViewById(R.id.tv_rumahsakit)
        val tvNomor: TextView = itemView.findViewById(R.id.tv_nomor)
        val tvJadwal: TextView = itemView.findViewById(R.id.tv_jadwal)
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_gambar)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: AhliGizi)
    }

}