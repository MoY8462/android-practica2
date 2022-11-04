package com.example.vpelis_practica2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * from movies ORDER BY title ASC")
    fun getItems(): MutableList<MovieEntity>

    @Query("SELECT * from movies WHERE id LIKE :id")
    fun getItem(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)
}