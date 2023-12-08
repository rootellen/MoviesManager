package br.edu.ifsp.scl.moviesmanager.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentAddMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.MoviesFragment.Companion.EXTRA_MOVIE
import br.edu.ifsp.scl.moviesmanager.view.MoviesFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY

class AddMovieFragment : Fragment() {
    private lateinit var famb: FragmentAddMovieBinding
    private val navigationArgs: AddMovieFragmentArgs by navArgs()
    private var ratingValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.add_a_movie)
        famb = FragmentAddMovieBinding.inflate(inflater, container, false)

        val receivedMovie = navigationArgs.movie
        receivedMovie?.also {
            with(famb) {
//                nameEt.setText(task.name)
            }
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
                putParcelable(
                    EXTRA_MOVIE, Movie(
                        receivedMovie?.name ?: famb.nameEt.text.toString(),
                        famb.yearEt.text.toString().toInt(),
                        famb.producerEt.text.toString(),
                        famb.durationEt.text.toString().toInt(),
                        famb.genreSp.selectedItem.toString(),
                        famb.watchedCb.isChecked,
                        ratingValue
                    )
                )
            })
            findNavController().navigateUp()
        }
        return famb.root
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