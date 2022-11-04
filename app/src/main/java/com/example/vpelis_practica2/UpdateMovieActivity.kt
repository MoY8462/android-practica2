package com.example.vpelis_practica2

import android.content.Context
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
import com.example.vpelis_practica2.databinding.ActivityUpdateMovieBinding
import java.security.AccessController.getContext

class UpdateMovieActivity : AppCompatActivity() {

    private lateinit var binnding: ActivityUpdateMovieBinding
    private lateinit var spinner: Spinner
    private lateinit var spinnerSelect : String

    private val moviesBD : MoviesDatabase by lazy {
        Room.databaseBuilder( this, MoviesDatabase::class.java, "movies_bd")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var movieEntity: MovieEntity
    private var movieId: Int = 0
    private var movieTitle = ""
    private var movieGenre = ""
    private var movieYear = ""
    private var movieAssesment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binnding = ActivityUpdateMovieBinding.inflate(layoutInflater)
        setContentView(binnding.root)

        var bundle : Bundle? = intent.extras
        var movieId = bundle?.getInt("bundle_movie_id")

        spinner = binnding.categorySpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        binnding.apply {
            var movie : MovieEntity
            if(movieId != null){
                movie = moviesBD.moviesDao().getItem(movieId)

                movieTitle = movie.title
                movieGenre = movie.category
                movieYear = movie.year
                movieAssesment = movie.assessment

                txtTitle.setText(movieTitle)
                categorySpinner.setTag(2)
                txtYear.setText(movieYear)
                txtAssesment.setText(movieAssesment)

                btnSave.setOnClickListener{
                    val title = binnding.txtTitle.text.toString()
                    val category = spinnerSelect
                    val year = binnding.txtYear.text.toString()
                    val assessment = binnding.txtAssesment.text.toString()

                    if(isEntryValid(title,category,year,assessment)){
                        movieEntity = MovieEntity(movieId,title,category,year,assessment)
                        moviesBD.moviesDao().update(movieEntity)
                        Toast.makeText(this@UpdateMovieActivity,"Cambios guardados", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@UpdateMovieActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@UpdateMovieActivity,"Verifica la información", Toast.LENGTH_LONG).show()
                    }
                }

                btnDelete.setOnClickListener {
                    movieEntity= MovieEntity(movieId,movieTitle,movieGenre,movieYear,movieAssesment)
                    moviesBD.moviesDao().delete(movieEntity)
                    finish()
                    val intent = Intent(this@UpdateMovieActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }

    fun isEntryValid(title:String, category: String, year: String, assessment:String): Boolean{
        if(title.isBlank() || category.isBlank() || year.isBlank() || assessment.isBlank()) {
            return false
        }
        return true
    }
}