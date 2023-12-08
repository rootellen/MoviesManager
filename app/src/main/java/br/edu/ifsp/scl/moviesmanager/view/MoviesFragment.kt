package br.edu.ifsp.scl.moviesmanager.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentMoviesBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener

class MoviesFragment : Fragment(), OnMovieClickListener {
    private lateinit var mfb: FragmentMoviesBinding
    private var ratingMoviePosition = 0

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }

    // Navigation controller
    private val navController: NavController by lazy {
        findNavController()
    }

    // View Model
//    private val viewModel: MoviesViewModel by viewModels {
//
//    }

    // Communication constants
    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val MOVIE_FRAGMENT_REQUEST_KEY = "MOVIE_FRAGMENT_REQUEST_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(MOVIE_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == MOVIE_FRAGMENT_REQUEST_KEY) {
                val movie =
                    bundle.getParcelable(EXTRA_MOVIE, Movie::class.java)
                movie?.also { receivedMovie ->
                    movieList.indexOfFirst { it.name == receivedMovie.name }.also { position ->
                        if (position != -1) {
//                            taskViewModel.editTask(receivedMovie)
                            movieList[position] = receivedMovie
                            movieAdapter.notifyItemChanged(position)
                        } else {
//                            taskViewModel.insertTask(receivedMovie)
                            movieList.add(receivedMovie)
                            movieAdapter.notifyItemInserted(movieList.lastIndex)
                        }
                    }
                }

                // Hiding soft keyboard
                (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    mfb.root.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Movie List"

        mfb = FragmentMoviesBinding.inflate(inflater, container, false).apply {
            movieRv.layoutManager = LinearLayoutManager(context)
            movieRv.adapter = movieAdapter

            addMovieFab.setOnClickListener {
                navController.navigate(
                    MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
                )
            }
        }
        return mfb.root
    }

    override fun onWatchedCheckboxClicked(position: Int, checked: Boolean) {
        if (checked) {
            val dialog = MovieRateDialogFragment(this)
            activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
            ratingMoviePosition = position
        }
    }

    override fun onRateButtonClicked(rateValue: Int) {
        movieList[ratingMoviePosition].run {
            watched = true
            rate = rateValue
        }
    }
}