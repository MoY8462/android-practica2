package com.example.vpelis_practica2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.vpelis_practica2.data.MovieEntity
import com.example.vpelis_practica2.data.MoviesDatabase
import com.example.vpelis_practica2.databinding.ActivityAddMovieBinding

class AddMovieActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddMovieBinding

    private val moviesBD : MoviesDatabase by lazy {
        Room.databaseBuilder( this, MoviesDatabase::class.java, "movies_bd")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var movieEntity: MovieEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

        }
    }
}