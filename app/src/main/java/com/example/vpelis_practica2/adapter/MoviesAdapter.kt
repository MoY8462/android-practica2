package com.example.vpelis_practica2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

                root.setOnClickListener {
                    /*
                    val intent= Intent(context,ListMovieFragment::class.java)
                    intent.putExtra(BUNDLE_NOTE_ID, item.id)
                    context.startActivity(intent)*/
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