package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.MovieRateDialogFragment

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
): RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(tileMovieBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileMovieBinding.root) {
        val nameTv: TextView = tileMovieBinding.nameTv
        val yearTv: TextView = tileMovieBinding.yearTv
        val genreTv: TextView = tileMovieBinding.genreTv
        val watchedCb: CheckBox = tileMovieBinding.watchedCb

        init {
            watchedCb.run {
                setOnClickListener {
                    onMovieClickListener.onWatchedCheckboxClicked(adapterPosition, isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { MovieTileViewHolder(this) }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        movieList[position].let {movie ->
            with(holder) {
                nameTv.text = movie.name
                yearTv.text = movie.year.toString()
                genreTv.text = movie.genre
                watchedCb.isChecked = movie.watched
            }
        }
    }
}