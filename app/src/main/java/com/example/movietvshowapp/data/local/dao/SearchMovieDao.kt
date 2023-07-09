package com.example.movietvshowapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietvshowapp.model.movie.NowPlayingMovie
import com.example.movietvshowapp.model.search.SearchMovie

@Dao
interface SearchMovieDao {
    @Query("SELECT * FROM search_movie_table")
    fun getAllImages(): PagingSource<Int, SearchMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<SearchMovie>)

    @Query("DELETE FROM search_movie_table")
    suspend fun deleteAllImages()
}