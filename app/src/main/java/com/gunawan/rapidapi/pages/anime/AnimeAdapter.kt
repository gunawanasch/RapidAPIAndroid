package com.gunawan.rapidapi.pages.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunawan.rapidapi.databinding.RowAnimeBinding
import com.gunawan.rapidapi.repositories.model.DataItemAnime

class AnimeAdapter(private val listAnime: List<DataItemAnime>) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {
    private var listener: OnCustomClickListener? = null

    interface OnCustomClickListener {
        fun onItemClicked(item: DataItemAnime)
    }

    fun setOnCustomClickListener(listener: OnCustomClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listAnime.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        Glide.with(holder.itemView.context).load((listAnime[position]?.image ?: "")).into(holder.binding.ivPoster)
        holder.binding.tvTitle.text     = listAnime[position]?.title ?: ""
        holder.binding.tvSynopsis.text  = listAnime[position]?.synopsis ?: ""
        holder.binding.cvAnime.setOnClickListener {
            listAnime[position]?.let { it1 ->
                listener?.onItemClicked(it1)
            }
        }
    }

    class ViewHolder(val binding: RowAnimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}