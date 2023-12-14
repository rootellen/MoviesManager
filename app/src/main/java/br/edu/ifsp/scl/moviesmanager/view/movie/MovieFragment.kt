package br.edu.ifsp.scl.moviesmanager.view.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentAddMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.movie.MainFragment.Companion.EXTRA_MOVIE
import br.edu.ifsp.scl.moviesmanager.view.movie.MainFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY
import br.edu.ifsp.scl.moviesmanager.viewModel.GenresViewModel

class MovieFragment : Fragment() {
    private lateinit var famb: FragmentAddMovieBinding
    private val navigationArgs: MovieFragmentArgs by navArgs()
    private var ratingValue = 0
    private var genreList = ArrayList<String>()
    private var adapterSp: ArrayAdapter<String>? = null
    private var receivedMovie: Movie? = null
    private var watchedMovie: Boolean? = null

    // Nav Controller
    private val navController: NavController by lazy {
        findNavController()
    }

    // Genres View Model
    private val genreViewModel: GenresViewModel by viewModels {
        GenresViewModel.genreViewModelFactory
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.add_a_movie)
        famb = FragmentAddMovieBinding.inflate(inflater, container, false)
        genreViewModel.genreMld.observe(viewLifecycleOwner) { genres ->
            genreList.clear()
            genres.forEach {
                genreList.add(it.genre)
            }
            configSpinner()
        }
        genreViewModel.getGenres()
        configSpinner()

        receivedMovie = navigationArgs.movie
        watchedMovie = receivedMovie?.watched
        receivedMovie?.also {
            with(famb) {
                nameEt.setText(receivedMovie?.name)
                nameEt.isEnabled = false
                yearEt.setText(receivedMovie?.year.toString())
                producerEt.setText(receivedMovie?.producer)
                durationEt.setText(receivedMovie?.duration.toString())
                genreSp.setSelection(adapterSp?.getPosition(receivedMovie?.genre) ?: 0)
                watchedCb.isChecked = receivedMovie?.watched ?: false
                rateRb.rating = receivedMovie?.rate?.toFloat()?.div(2) ?: 0f
                rateBarTv.text = "${(receivedMovie?.rate ?: 0)}/10"
                if (watchedCb.isChecked) {
                    rateRb.visibility = VISIBLE
                    rateBarTv.visibility = VISIBLE
                }
                if (!navigationArgs.editMovie) {
                    yearEt.isEnabled = false
                    producerEt.isEnabled = false
                    durationEt.isEnabled = false
                    genreSp.isEnabled = false
                    watchedCb.isEnabled = false
                    rateRb.setIsIndicator(true)
                    saveBt.isEnabled = false
                }
            }
        }

        famb.editGenreBt.setOnClickListener {
            navController.navigate(
                MovieFragmentDirections.actionAddMovieFragmentToGenreFragment()
            )
        }

        famb.watchedCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                famb.rateRb.visibility = VISIBLE
                famb.rateBarTv.visibility = VISIBLE
            } else {
                famb.rateRb.visibility = GONE
                famb.rateBarTv.visibility = GONE
            }
        }

        famb.rateRb.setOnRatingBarChangeListener { _, rating, _ ->
            "${(rating * 2).toInt()}/10".also { famb.rateBarTv.text = it }
            ratingValue = (rating * 2).toInt()
        }

        famb.saveBt.setOnClickListener {
            setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                val watched = famb.watchedCb.isChecked
                val rating = if (watched) { ratingValue } else { null }
                putParcelable(
                    EXTRA_MOVIE, Movie(
                        receivedMovie?.name ?: famb.nameEt.text.toString(),
                        famb.yearEt.text.toString().toInt(),
                        famb.producerEt.text.toString(),
                        famb.durationEt.text.toString().toInt(),
                        famb.genreSp.selectedItem.toString(),
                        watched,
                        rating
                    )
                )
            })
            findNavController().navigateUp()
        }
        return famb.root
    }
    private fun configSpinner() {
        adapterSp = activity?.let { ArrayAdapter<String>(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genreList) }
        famb.genreSp.adapter = adapterSp
        famb.genreSp.setSelection(adapterSp?.getPosition(receivedMovie?.genre) ?: 0)
    }
}