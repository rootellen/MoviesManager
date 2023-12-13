package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentAddMovieBinding
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentGenreBinding

class GenreFragment : Fragment() {
    private lateinit var fagb: FragmentGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Add a genre"
        fagb = FragmentGenreBinding.inflate(inflater, container, false)
        fagb.addGenreFab.setOnClickListener {
            val dialog = InsertGenreDialogFragment()
            activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
        }
        return fagb.root
    }

}