package br.edu.ifsp.scl.moviesmanager.view.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import br.edu.ifsp.scl.moviesmanager.databinding.GenreDialogBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre
import br.edu.ifsp.scl.moviesmanager.view.genre.adapter.GenreAdapter
import br.edu.ifsp.scl.moviesmanager.viewModel.GenresViewModel

class InsertGenreDialogFragment(
    private val viewModel: GenresViewModel,
    private val viewAdapter: GenreAdapter
): DialogFragment() {
    private lateinit var gdb: GenreDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gdb = GenreDialogBinding.inflate(inflater, container, false)
        gdb.insertBt.setOnClickListener {
            val genre = Genre(0, gdb.genreEt.text.toString())
            viewModel.insertGenre(genre)
            viewAdapter.genreList.add(genre)
            viewAdapter.notifyItemInserted(viewAdapter.genreList.lastIndex)
            dismiss()
        }
        gdb.cancelBt.setOnClickListener {
            dismiss()
        }
        return gdb.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}