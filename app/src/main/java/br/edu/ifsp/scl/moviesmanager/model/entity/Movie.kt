package br.edu.ifsp.scl.moviesmanager.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    var name: String,
    var year: Int,
    var producer: String,
    var duration: Int,
    var genre: String,
    var watched: Boolean = false,
    var rate: Int
) : Parcelable {
}