package me.pegbeer.filmku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import me.pegbeer.filmku.R
import me.pegbeer.filmku.databinding.CastCardBinding
import me.pegbeer.filmku.dto.CastDto

class CastListAdapter : ListAdapter<CastDto,CastListAdapter.ViewHolder>(CastDifferCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CastCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding:ViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CastDto) = with(binding as CastCardBinding){
            Glide.with(root)
                .load(root.resources.getString(R.string.imageUrl,item.profilePath))
                .into(castImage)

            castName.text = item.name
        }
    }
}

class CastDifferCallback() : DiffUtil.ItemCallback<CastDto>(){
    override fun areItemsTheSame(oldItem: CastDto, newItem: CastDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastDto, newItem: CastDto): Boolean {
        return oldItem == newItem
    }

}