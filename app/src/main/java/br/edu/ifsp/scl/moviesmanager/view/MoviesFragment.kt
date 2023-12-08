package br.edu.ifsp.scl.moviesmanager.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentMoviesBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter

class MoviesFragment : Fragment() {
    private lateinit var mfb: FragmentMoviesBinding

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList)
    }

    // Navigation controller
    private val navController: NavController by lazy {
        findNavController()
    }

    // View Model
//    private val viewModel: MoviesViewModel by viewModels {
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMovieFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}