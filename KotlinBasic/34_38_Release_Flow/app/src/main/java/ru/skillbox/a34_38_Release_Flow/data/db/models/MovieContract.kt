package ru.skillbox.a34_38_Release_Flow.data.db.models

object MovieContract {
    const val TABLE_NAME = "movies"

    object Columns {
        const val ID = "id"
        const val IMDB_ID = "imdb_id"
        const val TITLE = "title"
        const val TYPE = "type"
        const val YEAR = "year"
        const val POSTER = "poster"
    }
}