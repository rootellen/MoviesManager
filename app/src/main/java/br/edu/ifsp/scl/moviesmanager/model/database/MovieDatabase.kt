package br.edu.ifsp.scl.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.moviesmanager.model.dao.GenreDAO
import br.edu.ifsp.scl.moviesmanager.model.dao.MovieDAO
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class, Genre::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    companion object {
        const val MOVIE_DATABASE = "movieDatabase"
    }
    abstract fun getMovieDao(): MovieDAO
    abstract fun getGenreDao() : GenreDAO
}