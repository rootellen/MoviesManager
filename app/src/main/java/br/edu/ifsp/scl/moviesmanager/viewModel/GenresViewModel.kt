package br.edu.ifsp.scl.moviesmanager.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel(application: Application) : ViewModel() {
    private val genreDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDatabase::class.java,
        MovieDatabase.MOVIE_DATABASE
    ).build().getGenreDao()

    val genreMld = MutableLiveData<List<Genre>>()

    fun getGenres() {
        CoroutineScope(Dispatchers.IO).launch {
            val genres = genreDaoImpl.retrieveGenres()
            genreMld.postValue(genres)
        }
    }

    fun insertGenre(genre: Genre) {
        CoroutineScope(Dispatchers.IO).launch {
            genreDaoImpl.createGenre(genre)
        }
    }

    fun removeGenre(genre: Genre) {
        CoroutineScope(Dispatchers.IO).launch {
            genreDaoImpl.deleteGenre(genre)
        }
    }

    companion object {
        val genreViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
                GenresViewModel(checkNotNull( extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
        }
    }
}