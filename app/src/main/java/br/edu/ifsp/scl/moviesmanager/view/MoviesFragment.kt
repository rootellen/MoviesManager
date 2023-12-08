package br.edu.ifsp.scl.moviesmanager.view

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentMoviesBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener
import br.edu.ifsp.scl.moviesmanager.viewModel.MoviesViewModel

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
    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModel.movieViewModelFactory
    }

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
                            viewModel.editMovie(receivedMovie)
                            movieList[position] = receivedMovie
                            movieAdapter.notifyItemChanged(position)
                        } else {
                            viewModel.insertMovie(receivedMovie)
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
        viewModel.movieMld.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, movie ->
                movieList.add(movie)
                movieAdapter.notifyItemChanged(index)
            }
        }
        viewModel.getMovies()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_fragment_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
                searchIcon.setColorFilter(Color.WHITE)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        movieAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onMovieClick(position: Int) = navigateToMovieFragment(position, false)

    override fun onRemoveMovieMenuItemClick(position: Int) {
        viewModel.removeMovie(movieList[position])
        movieList.removeAt(position)
        movieAdapter.notifyItemRemoved(position)
    }

    override fun onEditMovieMenuItemClick(position: Int) = navigateToMovieFragment(position, true)

    override fun onWatchedCheckboxClicked(position: Int, checked: Boolean) {
        ratingMoviePosition = position
        if (checked) {
            val dialog = MovieRateDialogFragment(this)
            activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
        } else {
            movieList[position].run {
                watched = false
                rate = null
            }
            viewModel.editMovie(movieList[position])
            movieAdapter.notifyItemChanged(position)
        }
    }

    override fun onCancelButtonDialogClicked() {
        movieList[ratingMoviePosition].run {
            this.watched = false
            this.rate = null
            viewModel.editMovie(this)
            movieAdapter.notifyItemChanged(ratingMoviePosition)
        }
    }

    override fun onRateButtonDialogClicked(rate: Int) {
        movieList[ratingMoviePosition].run {
            this.watched = true
            this.rate = rate
            viewModel.editMovie(this)
            movieAdapter.notifyItemChanged(ratingMoviePosition)
        }
    }

    private fun navigateToMovieFragment(position: Int, editMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment(it, editMovie)
            )
        }
    }
}