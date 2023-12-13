package br.edu.ifsp.scl.moviesmanager.view.movie.adapter

interface OnMovieClickListener {
    fun onMovieClick(position: Int)
    fun onRemoveMovieMenuItemClick(position: Int)
    fun onEditMovieMenuItemClick(position: Int)
    fun onWatchedCheckboxClicked(position: Int, checked: Boolean)
    fun onRateButtonDialogClicked(rate: Int)

    fun onCancelButtonDialogClicked()
}