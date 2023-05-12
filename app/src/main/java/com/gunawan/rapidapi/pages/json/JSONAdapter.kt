package com.gunawan.rapidapi.pages.json

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.rapidapi.databinding.RowJsonBinding
import com.gunawan.rapidapi.repositories.model.JSONResp

class JSONAdapter(private val listJSON: List<JSONResp>) : RecyclerView.Adapter<JSONAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowJsonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listJSON.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.binding.tvName.text  = listJSON[position].name
        holder.binding.tvEmail.text = listJSON[position].email
    }

    class ViewHolder(val binding: RowJsonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}