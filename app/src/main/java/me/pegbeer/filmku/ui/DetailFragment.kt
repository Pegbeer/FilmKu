package me.pegbeer.filmku.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.pegbeer.filmku.adapter.CastListAdapter
import me.pegbeer.filmku.adapter.GenreListAdapter
import me.pegbeer.filmku.databinding.FragmentDetailBinding
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.viewmodel.DetailViewModel
import org.koin.android.ext.android.inject
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.util.toHourMinuteString

class DetailFragment : Fragment() {

    lateinit var binding:FragmentDetailBinding

    private val args:DetailFragmentArgs by navArgs()
    private val  viewModel:DetailViewModel by inject()

    private val castAdapter = CastListAdapter()
    private val genresAdapter = GenreListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBindings()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycle.addObserver(binding.trailerPlayer)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movie.collect{
                when(it.status){
                    Result.Status.LOADING ->{
                        binding.detailSkeletonView.showSkeleton()
                    }
                    Result.Status.SUCCESS->{
                        setBindings(it.data!!)
                        binding.detailSkeletonView.showOriginal()
                    }
                    Result.Status.ERROR ->{
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage("Could not process the request\nError code:${it.code}")
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    private fun setBindings(movie:MovieDetail) {
        binding.movie = movie
        binding.lengthTextView.text = movie.length.toHourMinuteString()
        binding.movieDetailGenresRecyclerview.adapter = genresAdapter
        binding.castRecyclerview.adapter = castAdapter
        genresAdapter.submitList(movie.genres)
        castAdapter.submitList(movie.cast)

        binding.trailerPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(movie.videoKey,0f)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Log.e(TAG, "onError: $error" )
            }
        })

        binding.detailsBackArrow.setOnClickListener(::navigateBack)

    }

    private fun navigateBack(view: View?) {
        findNavController().navigateUp()
    }

    private fun initBindings() {
        viewModel.getMovieDetails(args.id)
    }


    companion object{
        const val TAG = "DetailFragment"
    }
}