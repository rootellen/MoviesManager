package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import br.edu.ifsp.scl.moviesmanager.databinding.GenreDialogBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre

class InsertGenreDialogFragment: DialogFragment() {
    private lateinit var gdb: GenreDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gdb = GenreDialogBinding.inflate(inflater, container, false)
        gdb.insertBt.setOnClickListener {
            Genre(0, gdb.genreEt.text.toString())
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