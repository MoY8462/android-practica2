package com.example.vpelis_practica2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * from movies ORDER BY title ASC")
    fun getItems(): MutableList<MovieEntity>

    @Query("SELECT * from movies WHERE id = :id")
    fun getItem(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)
}