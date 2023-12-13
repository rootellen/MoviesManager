package br.edu.ifsp.scl.moviesmanager.view.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.databinding.TileGenreBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre

class GenreAdapter(private val onGenreClickListener: onGenreClickListener):
    RecyclerView.Adapter<GenreAdapter.GenreTileViewHolder>() {

    var genreList = ArrayList<Genre>()

    inner class GenreTileViewHolder(tileGenreBinding: TileGenreBinding) :
        RecyclerView.ViewHolder(tileGenreBinding.root) {
            val genreTv: TextView = tileGenreBinding.genreTv

            init {
                tileGenreBinding.trashButton.setOnClickListener {
                    onGenreClickListener.onDeleteClickListener(adapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TileGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false
        ).run { GenreTileViewHolder(this) }

    override fun getItemCount(): Int = genreList.size

    override fun onBindViewHolder(holder: GenreTileViewHolder, position: Int) {
        genreList[position].let { genre ->
            with(holder) {
                genreTv.text = genre.genre
            }
        }
    }

}