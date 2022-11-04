package com.example.vpelis_practica2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vpelis_practica2.R
import com.example.vpelis_practica2.UpdateMovieActivity
import com.example.vpelis_practica2.data.MovieEntity
import com.example.vpelis_practica2.databinding.CustomElementBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private lateinit var binding: CustomElementBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CustomElementBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MovieEntity) {
            //InitView
            binding.apply {
                //Set text
                txtMovie.text = item.title
                txtGenre.text = item.category
                txtAssessment.text = item.assessment
                when (item.category){
                    "comedy" -> {   imgGenre.setImageResource(R.drawable.comedy_movie)
                                    txtGenre.text = "Comedia"}
                    "action" -> {   imgGenre.setImageResource(R.drawable.action_movie)
                                    txtGenre.text = "Acción"}
                    "horror" -> {   imgGenre.setImageResource(R.drawable.horror_movies)
                                    txtGenre.text = "Terror"}
                    "childish" -> { imgGenre.setImageResource(R.drawable.child_movie)
                                    txtGenre.text = "Infantiles"}
                    "documentaries" -> {    imgGenre.setImageResource(R.drawable.documentaries_movie)
                                            txtGenre.text = "Documentales"}
                }

                root.setOnClickListener {

                    val intent= Intent(context,UpdateMovieActivity::class.java)
                    intent.putExtra("bundle_movie_id", item.id)
                    context.startActivity(intent)
                }

            }
        }
    }

    val differCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }

    val  differ =  AsyncListDiffer(this, differCallback)



}