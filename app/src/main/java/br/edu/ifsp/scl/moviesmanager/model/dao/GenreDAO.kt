package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre

@Dao
interface GenreDAO {
    companion object {
        const val GENRE_TABLE = "genre"
    }
    @Query("SELECT * FROM $GENRE_TABLE")
    fun retrieveGenres(): List<Genre>
    @Insert
    fun createGenre(genre: Genre)
    @Delete
    fun deleteGenre(genre: Genre)
}