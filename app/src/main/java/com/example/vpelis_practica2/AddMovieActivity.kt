package com.example.vpelis_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.room.Room
import com.example.vpelis_practica2.data.MovieEntity
import com.example.vpelis_practica2.data.MoviesDatabase
import com.example.vpelis_practica2.databinding.ActivityAddMovieBinding

class AddMovieActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddMovieBinding
    private lateinit var spinner: Spinner
    private lateinit var spinnerSelect : String

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
        spinner = binding.categorySpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            R.layout.spinner_select
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_items)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                when (pos){
                    0 -> { spinnerSelect = "action"}
                    1 -> { spinnerSelect = "comedy"}
                    2 -> { spinnerSelect = "horror"}
                    3 -> { spinnerSelect = "childish"}
                    4 -> { spinnerSelect = "documentaries"}
                    5 -> { spinnerSelect = "family"}
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        binding.apply {
            saveAction.setOnClickListener{
                val title = binding.txtTitle.text.toString()
                val category = spinnerSelect
                val year = binding.txtYear.text.toString()
                val assessment = binding.txtAssesment.text.toString()

                when {
                    title.isEmpty() -> {
                        binding.txtTitle.error = getString(R.string.require)
                    }
                    year.isEmpty() -> {
                        binding.txtYear.error = getString(R.string.require)
                    }
                    year.toFloat() <= 1500 -> {
                        binding.txtYear.error = getString(R.string.year_error)
                    }
                    year.toFloat() >= 2025 -> {
                        binding.txtYear.error = getString(R.string.year_error)
                    }
                    assessment.isEmpty() -> {
                        binding.txtAssesment.error = getString(R.string.require)
                    }
                    else -> {
                        movieEntity = MovieEntity(0,title,category,year,assessment)
                        moviesBD.moviesDao().insert(movieEntity)
                        Toast.makeText(this@AddMovieActivity,getString(R.string.add_movie), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@AddMovieActivity, MainActivity::class.java)
                        startActivity(intent)
                    }


                }

            }
        }
    }

}