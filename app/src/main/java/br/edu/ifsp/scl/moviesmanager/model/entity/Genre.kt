package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Genre(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var genre: String
) : Parcelable {
}