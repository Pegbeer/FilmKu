package me.pegbeer.filmku.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import me.pegbeer.filmku.adapter.LoaderAdapter
import me.pegbeer.filmku.adapter.MovieListAdapter
import me.pegbeer.filmku.adapter.RegularMovieListAdapter
import me.pegbeer.filmku.databinding.FragmentHomeBinding
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel:HomeViewModel by inject()

    private lateinit var binding: FragmentHomeBinding

    private val nowPlayingAdapter = MovieListAdapter(0,::onMovieClicked)
    private val popularAdapter = RegularMovieListAdapter(1,::onMovieClicked)

    private fun onMovieClicked(movieWithGenres: MovieWithGenres) {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
    }

    private fun initAdapters() {
        binding.nowShowingRecyclerView.adapter = nowPlayingAdapter
        binding.popularRecyclerview.adapter = popularAdapter
        nowPlayingAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.nowShowingRecyclerView.apply {
            setHasFixedSize(true)
            this.adapter = nowPlayingAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }

        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMovies.collect{
                nowPlayingAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collect{
                when(it.status){
                    Result.Status.SUCCESS->{
                        popularAdapter.submitList(it.data)
                    }
                    else ->{
                        AlertDialog.Builder(requireContext())
                            .setTitle("Warning")
                            .setMessage("An error occurred processing the request")
                            .create()
                            .show()
                    }
                }
            }
        }
    }


}