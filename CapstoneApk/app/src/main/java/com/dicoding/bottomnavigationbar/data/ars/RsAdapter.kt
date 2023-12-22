package com.dicoding.bottomnavigationbar.data.ars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bottomnavigationbar.databinding.ItemRsBinding

class RsAdapter(private val listRs: List<Rs>) : RecyclerView.Adapter<RsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: ItemRsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rs: Rs) {
            binding.tvNamaRs.text = rs.nama
            binding.tvAlamatRs.text = rs.alamatRS
            Glide.with(itemView.context)
                .load(rs.photo)
                .into(binding.ivRs)

            itemView.setOnClickListener { onItemClickCallback.onItemClicked(rs) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listRs.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val rs = listRs[position]
        holder.bind(rs)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Rs)
    }
}