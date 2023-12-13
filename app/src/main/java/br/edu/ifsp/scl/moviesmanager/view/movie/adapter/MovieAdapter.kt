package br.edu.ifsp.scl.moviesmanager.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

class MovieAdapter(
    private val onMovieClickListener: OnMovieClickListener
): RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>(), Filterable {

    var movieList = ArrayList<Movie>()
    var movieListFilterable = ArrayList<Movie>()
    inner class MovieTileViewHolder(tileMovieBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileMovieBinding.root) {
        val nameTv: TextView = tileMovieBinding.nameTv
        val yearTv: TextView = tileMovieBinding.yearTv
        val genreTv: TextView = tileMovieBinding.genreTv
        val watchedCb: CheckBox = tileMovieBinding.watchedCb

        init {
            tileMovieBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_movie,
                            menu
                        )
                        menu?.findItem(R.id.removeMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
            }
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

    override fun getItemCount(): Int = movieListFilterable.size

    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        movieListFilterable[position].let {movie ->
            with(holder) {
                nameTv.text = movie.name
                yearTv.text = movie.year.toString()
                genreTv.text = movie.genre
                watchedCb.isChecked = movie.watched
            }
        }
    }

    override fun getFilter(): Filter {
        return object  : Filter() {
            override fun performFiltering(p: CharSequence?): FilterResults {
                if (p.toString().isEmpty()) {
                    movieListFilterable = movieList
                }
                else {
                    val resultList = ArrayList<Movie>()
                    for (row in movieList) {
                        if (
                            row.name.lowercase().contains(p.toString().lowercase()) ||
                            row.rate.toString() == p.toString()
                        ) {
                            resultList.add(row)
                        }
                    }
                    movieListFilterable = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = movieListFilterable
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                movieListFilterable = p1?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    fun updateList(list: List<Movie>) {
        movieList = list as ArrayList<Movie>
        movieListFilterable = movieList
        notifyDataSetChanged()
    }
}