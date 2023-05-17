package com.adrino.moviemate.components.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adrino.moviemate.R
import com.adrino.moviemate.model.Movie
import com.bumptech.glide.RequestManager
import com.facebook.shimmer.ShimmerFrameLayout

interface MovieItemClickListener {
    fun onClick(context: Context?, movie: Movie)
}

class MoviesAdapter(
    private val context: Context?,
    private var movies: ArrayList<Movie>?,
    private val glide: RequestManager?,
    private val onItemClickListener: MovieItemClickListener
) : RecyclerView.Adapter<MoviesAdapter.VH_Movies>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH_Movies {
        return VH_Movies(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH_Movies, position: Int) {
        movies?.get(position)?.let { movie ->
            holder.cvCardContainer.setOnClickListener{ onItemClickListener.onClick(context, movie) }
            holder.tvMovieName.text = movie.movieName
            glide?.load(movie.getPosterURL())?.error(R.drawable.image_place_holder)
                ?.into(holder.ivMovieImage)
        }
    }

    fun addMovies(newMovies: List<Movie>) {
        if (movies == null) {
            movies = arrayListOf()
        }
        movies?.clear()
        movies?.addAll(newMovies)
        notifyDataSetChanged()
    }

    class VH_Movies(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMovieName: AppCompatTextView
        var ivMovieImage: AppCompatImageView
        var cvCardContainer: CardView

        init {
            tvMovieName = itemView.findViewById(R.id.tvMovieName)
            ivMovieImage = itemView.findViewById(R.id.ivMovieImage)
            cvCardContainer = itemView.findViewById(R.id.cvCardContainer)
        }
    }
}