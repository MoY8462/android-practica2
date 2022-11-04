package com.example.vpelis_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.vpelis_practica2.adapter.MoviesAdapter
import com.example.vpelis_practica2.data.MoviesDatabase
import com.example.vpelis_practica2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val moviesBD : MoviesDatabase by lazy {
        Room.databaseBuilder( this, MoviesDatabase::class.java, "movies_bd")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val movieAdapter by lazy { MoviesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddMovie.setOnClickListener {
            startActivity(Intent(this, AddMovieActivity::class.java))
        }


    }
    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
            if(moviesBD.moviesDao().getItems().isNotEmpty()){
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                imgPelis.visibility = View.GONE
                movieAdapter.differ.submitList(moviesBD.moviesDao().getItems())
                setupRecyclerView()
            } else {
                rvNoteList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
                imgPelis.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvNoteList.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=movieAdapter
        }

    }
}