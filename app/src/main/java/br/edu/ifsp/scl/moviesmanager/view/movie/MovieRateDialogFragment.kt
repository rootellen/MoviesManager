package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import br.edu.ifsp.scl.moviesmanager.databinding.RateDialogBinding
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener

class MovieRateDialogFragment(private val onMovieClickListener: OnMovieClickListener): DialogFragment() {
    private lateinit var mrdfb: RateDialogBinding
    private var ratingValue = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mrdfb = RateDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setDimAmount(0.9f)
        mrdfb.rateMovieRb.setOnRatingBarChangeListener { _, rating, _ ->
            "${(rating * 2).toInt()}/10".also { mrdfb.rateBarTv.text = it }
            ratingValue = (rating * 2).toInt()
        }

        mrdfb.cancelBt.setOnClickListener {
            onMovieClickListener.onCancelButtonDialogClicked()
            dismiss()
        }

        mrdfb.saveBt.setOnClickListener {
            onMovieClickListener.onRateButtonDialogClicked(ratingValue)
            dismiss()
        }
        return mrdfb.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}