package ru.skillbox.a34_38_Release_Flow.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.skillbox.a34_38_Release_Flow.data.Movie

@Entity(
    tableName = MovieContract.TABLE_NAME,
    indices = [Index(
        value = [MovieContract.Columns.IMDB_ID],
        unique = true
    )]
)
data class MovieDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MovieContract.Columns.ID)
    val id: Long,

    @ColumnInfo(name = MovieContract.Columns.IMDB_ID)
    val imdbId: String,

    @ColumnInfo(name = MovieContract.Columns.TITLE)
    val title: String,

    @ColumnInfo(name = MovieContract.Columns.TYPE)
    val type: String,

    @ColumnInfo(name = MovieContract.Columns.YEAR)
    val year: String,

    @ColumnInfo(name = MovieContract.Columns.POSTER)
    val poster: String
) {
    companion object {
        fun convertFromResponse(movie: Movie) = MovieDB(
            0,
            movie.id,
            movie.title,
            movie.type,
            movie.year,
            movie.poster
        )

        fun convertFromDb(movieDb: MovieDB) = Movie(
            movieDb.imdbId,
            movieDb.title,
            movieDb.type,
            movieDb.year,
            movieDb.poster
        )
    }
}
