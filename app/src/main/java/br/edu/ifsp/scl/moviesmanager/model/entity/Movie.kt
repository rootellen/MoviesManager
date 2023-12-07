package br.edu.ifsp.scl.moviesmanager.model.entity

class Movie(
    var name: String,
    var year: Int,
    var producer: String,
    var duration: Int,
    var watched: Boolean = false,
    var rate: Int,
    var genre: String
) {
}