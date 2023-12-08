package br.edu.ifsp.scl.moviesmanager.view.adapter

interface OnMovieClickListener {
    fun onWatchedCheckboxClicked(position: Int, checked: Boolean)
    fun onRateButtonClicked(rate: Int)
}