package com.adrino.moviemate.review

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adrino.moviemate.R
import com.adrino.moviemate.REVIEW_MOVIE
import com.adrino.moviemate.databinding.ActivityMovieReviewBinding
import com.adrino.moviemate.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class MovieReviewActivity : AppCompatActivity() {

    private var reviewMovie: Movie? = null
    private lateinit var binding: ActivityMovieReviewBinding
    private lateinit var glide: RequestManager

    companion object {
        fun createInstance(context: Context?, reviewMovie: Movie): Intent {
            return Intent(context, MovieReviewActivity::class.java).apply {
                putExtra(REVIEW_MOVIE, reviewMovie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_review)
        glide = Glide.with(this)
        getIntentExtra()
        initView()
    }

    private fun getIntentExtra() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            reviewMovie = intent.getParcelableExtra(REVIEW_MOVIE, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            reviewMovie = intent.getParcelableExtra(REVIEW_MOVIE) as Movie?
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.tvTitle.text = reviewMovie?.movieName?: "Movie"
        binding.tvSubTitle.text = "Rating : " + String.format("%.1f", reviewMovie?.vote_average?: 0) + "/10"
        reviewMovie?.let { movie ->
            glide.load(movie.getBackdropURL()).error(R.drawable.image_place_holder).into(binding.ivImage)
        }
        binding.tvContentOverview.text = reviewMovie?.overview?: "NA"
        binding.tvVoteCount.text = reviewMovie?.vote_count?.toString()?: "NA"
        binding.tvLaunchDate.text = reviewMovie?.first_air_date?: "NA"
    }
}