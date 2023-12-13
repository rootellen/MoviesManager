package br.edu.ifsp.scl.moviesmanager.view.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentGenreBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre
import br.edu.ifsp.scl.moviesmanager.view.genre.adapter.GenreAdapter
import br.edu.ifsp.scl.moviesmanager.view.genre.adapter.onGenreClickListener
import br.edu.ifsp.scl.moviesmanager.viewModel.GenresViewModel

class GenreFragment : Fragment(), onGenreClickListener {
    private lateinit var fagb: FragmentGenreBinding

    // Adapter
    private val genreAdapter: GenreAdapter by lazy {
        GenreAdapter(this)
    }

    // View Model
    private val viewModel: GenresViewModel by viewModels {
        GenresViewModel.genreViewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Add a genre"
        fagb = FragmentGenreBinding.inflate(inflater, container, false)
        fagb.genreRv.layoutManager = LinearLayoutManager(context)
        fagb.genreRv.adapter = genreAdapter

        fagb.addGenreFab.setOnClickListener {
            val dialog = InsertGenreDialogFragment(viewModel, genreAdapter)
            activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
        }

        viewModel.genreMld.observe(viewLifecycleOwner) { genres ->
            genreAdapter.genreList = genres as ArrayList<Genre>
            genreAdapter.notifyDataSetChanged()
        }

        viewModel.getGenres()
        return fagb.root
    }

    override fun onDeleteClickListener(position: Int) {
        viewModel.removeGenre(genreAdapter.genreList[position])
        genreAdapter.genreList.removeAt(position)
        genreAdapter.notifyItemRemoved(position)
    }

}