package ru.skillbox.a30_34_flow.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import ru.skillbox.a30_34_flow.data.db.models.MovieDB

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovies(movies: List<MovieDB>)
}