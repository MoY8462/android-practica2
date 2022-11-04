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
                when (movieGenre){
                    "comedy" -> {   imgPelis.setImageResource(R.drawable.comedy_movie)
                                    categorySpinner.setSelection(1)}
                    "action" -> {   imgPelis.setImageResource(R.drawable.action_movie)
                                    categorySpinner.setSelection(0)}
                    "horror" -> {   imgPelis.setImageResource(R.drawable.horror_movies)
                                    categorySpinner.setSelection(2)}
                    "childish" -> { imgPelis.setImageResource(R.drawable.child_movie)
                                    categorySpinner.setSelection(3)}
                    "documentaries" -> { imgPelis.setImageResource(R.drawable.documentaries_movie)
                                        categorySpinner.setSelection(4)}
                }

                txtYear.setText(movieYear)
                txtAssesment.setText(movieAssesment)

                btnSave.setOnClickListener{
                    val title = binnding.txtTitle.text.toString()
                    val category = spinnerSelect
                    val year = binnding.txtYear.text.toString()
                    val assessment = binnding.txtAssesment.text.toString()

                    when {
                        title.isEmpty() -> {
                            binnding.txtTitle.error = getString(R.string.require)
                        }
                        year.isEmpty() -> {
                            binnding.txtYear.error = getString(R.string.require)
                        }
                        assessment.isEmpty() -> {
                            binnding.txtAssesment.error = getString(R.string.require)
                        }
                        year.toFloat() <= 1500 -> {
                            binnding.txtYear.error = getString(R.string.year_error)
                        }
                        year.toFloat() >= 2025 -> {
                            binnding.txtYear.error = getString(R.string.year_error)
                        }
                        else -> {
                            movieEntity = MovieEntity(movieId,title,category,year,assessment)
                            moviesBD.moviesDao().update(movieEntity)
                            Toast.makeText(this@UpdateMovieActivity,getString(R.string.save_changes), Toast.LENGTH_LONG).show()
                            val intent = Intent(this@UpdateMovieActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
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

}