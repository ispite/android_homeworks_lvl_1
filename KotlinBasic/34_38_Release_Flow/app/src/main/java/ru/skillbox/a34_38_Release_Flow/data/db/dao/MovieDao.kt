package ru.skillbox.a34_38_Release_Flow.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieContract
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieDB

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieDB>)

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Columns.TITLE} LIKE '%'||:title||'%' AND ${MovieContract.Columns.TYPE} = lower(:type)")
    suspend fun getMovieByTitleAndType(title: String, type: String): List<MovieDB>

    //    @Query("SELECT * FROM ${MovieContract.TABLE_NAME} WHERE active = 1")
    @Query("SELECT * FROM ${MovieContract.TABLE_NAME}")
    fun observeMovies(): Flow<List<MovieDB>>
}