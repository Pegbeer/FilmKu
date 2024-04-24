package me.pegbeer.filmku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import me.pegbeer.filmku.databinding.GenreChipBinding
import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.local.entity.GenreEntity

class GenreListAdapter: ListAdapter<GenreDto,GenreListAdapter.ViewHolder>(GenreDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GenreChipBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding:ViewBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item:GenreDto){
            (binding as GenreChipBinding).apply {
                genre = item
                executePendingBindings()
            }
        }
    }
}

class GenreDiffCallback : DiffUtil.ItemCallback<GenreDto>(){
    override fun areItemsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem == newItem
    }
}