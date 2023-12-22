package com.dicoding.bottomnavigationbar.data.artikel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.databinding.ItemArtikelBinding

class ArtikelAdapter(private val listArtikel: List<Artikel>) : RecyclerView.Adapter<ArtikelAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: ItemArtikelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: Artikel) {
            binding.tvJudulArtikel.text = artikel.judulArtikel
            Glide.with(itemView.context)
                .load(artikel.imgArtikel)
                .into(binding.ivArtikel)

            itemView.setOnClickListener { onItemClickCallback.onItemClicked(artikel) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listArtikel.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val artikel = listArtikel[position]
        holder.bind(artikel)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Artikel)
    }
}
