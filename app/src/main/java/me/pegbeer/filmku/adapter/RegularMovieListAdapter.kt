package me.pegbeer.filmku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import me.pegbeer.filmku.R
import me.pegbeer.filmku.databinding.MovieHorizontalCardBinding
import me.pegbeer.filmku.databinding.MovieVerticalCardBinding
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.util.toHourMinuteString

class RegularMovieListAdapter(
    private val type: Int,
    private val onClick: (Long) -> Unit
) : ListAdapter<MovieDetail,RegularMovieListAdapter.ViewHolder>(MovieDiffCallback()) {

    class ViewHolder(private val binding:ViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindVertical(item: MovieDetail?, onClick:(Long) -> Unit) {
            (binding as MovieVerticalCardBinding).apply {
                if(item != null){
                    movie = item
                    Glide.with(root).load(
                        root.resources.getString(R.string.imageUrl,item.posterPath)
                    ).into(movieImageView)
                    root.setOnClickListener { onClick(item.id) }
                }
                executePendingBindings()
            }
        }

        fun bindHorizontal(item: MovieDetail?,onClick:(Long) -> Unit) {
            val genreListAdapter = GenreListAdapter()
            (binding as MovieHorizontalCardBinding).apply {
                if(item != null){
                    movie = item
                    movieLength.text = item.length.toHourMinuteString()
                    movieGenresRecyclerview.adapter = genreListAdapter
                    if(item.genres.isNotEmpty() && item.genres.size > 3){
                        genreListAdapter.submitList(item.genres.subList(0,2))
                    }else{
                        genreListAdapter.submitList(item.genres)
                    }
                    Glide.with(root)
                        .load(root.resources.getString(R.string.imageUrl,item.posterPath))
                        .into(moviePosterImageView)
                    root.setOnClickListener { onClick(item.id) }
                }
                executePendingBindings()
            }
        }

        companion object {
            fun fromVertical(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieVerticalCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

            fun fromHorizontal(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieHorizontalCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(type) {
            0 -> ViewHolder.fromVertical(parent)
            else -> ViewHolder.fromHorizontal(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(type) {
            0 -> holder.bindVertical(getItem(position),onClick)
            else -> holder.bindHorizontal(getItem(position),onClick)
        }
    }


}