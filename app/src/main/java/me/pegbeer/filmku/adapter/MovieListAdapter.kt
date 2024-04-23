package me.pegbeer.filmku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import me.pegbeer.filmku.R
import me.pegbeer.filmku.databinding.MovieHorizontalCardBinding
import me.pegbeer.filmku.databinding.MovieVerticalCardBinding
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.util.toHourMinuteString
import org.koin.android.ext.android.get

class MovieListAdapter(private val type: Int, private val movieClickListener: (MovieWithGenres) -> Unit) :
    PagingDataAdapter<MovieDetail, MovieListAdapter.ViewHolder>(MovieDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(type) {
            0 -> holder.bindVertical(getItem(position),movieClickListener)
            else -> holder.bindHorizontal(getItem(position),movieClickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when(type) {
            0 -> ViewHolder.fromVertical(parent)
            else -> ViewHolder.fromHorizontal(parent)
        }
    }

    class ViewHolder private constructor(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindVertical(item: MovieDetail?, onClick:(MovieWithGenres) -> Unit) {
            (binding as MovieVerticalCardBinding).apply {
                if(item != null){
                    movie = item
                    Glide.with(root)
                        .load(
                        root.resources.getString(R.string.imageUrl,item.posterPath))
                        .override(movieImageView.width,movieImageView.height)
                        .into(movieImageView)
                }
                executePendingBindings()
            }
        }

        fun bindHorizontal(item: MovieDetail?,onClick:(MovieWithGenres) -> Unit) {
            val genreListAdapter = GenreListAdapter()
            (binding as MovieHorizontalCardBinding).apply {
                if(item != null){
                    movie = item
                    movieLength.text = ""
                    movieGenresRecyclerview.adapter = genreListAdapter
                    genreListAdapter.submitList(item.genres.subList(0,2))
                    Glide.with(root)
                        .load(root.resources.getString(R.string.imageUrl,item.posterPath))
                        .override(moviePosterImageView.width,moviePosterImageView.height)
                        .into(moviePosterImageView)
                    movieLength.text = item.length.toHourMinuteString()
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
}

class MovieDiffCallback: DiffUtil.ItemCallback<MovieDetail>() {
    override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
        return oldItem == newItem
    }
}