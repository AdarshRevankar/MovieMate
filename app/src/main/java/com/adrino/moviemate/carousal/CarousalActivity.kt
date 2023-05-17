package com.adrino.moviemate.carousal

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.adrino.moviemate.R
import com.adrino.moviemate.components.recyclerView.MovieItemClickListener
import com.adrino.moviemate.components.recyclerView.MoviesAdapter
import com.adrino.moviemate.databinding.ActivityCarousalBinding
import com.adrino.moviemate.model.Movie
import com.adrino.moviemate.model.TMDBResponse
import com.adrino.moviemate.review.MovieReviewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.orhanobut.logger.Logger


class CarousalActivity : AppCompatActivity(), CarousalContract.View {

    private lateinit var binding: ActivityCarousalBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var presenter: CarousalContract.Presenter
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var glide: RequestManager

    companion object {
        private const val DEFAULT_GRID_SPAN_COUNT = 2

        fun createInstance(context: Context?): Intent {
            return Intent(context, CarousalActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_carousal)
        loadingDialog = getLoadingDialog()
        glide = Glide.with(this)
        presenter = CarousalPresenterImpl(this, this)

        initView()
        presenter.fetchMovieData()
    }

    private fun initView() {
        binding.rvMovies.visibility = View.GONE
        binding.tvBroken.visibility = View.GONE
        binding.ivBroken.visibility = View.GONE
        binding.btnBroken.visibility = View.GONE
        binding.btnBroken.setOnClickListener { presenter.fetchMovieData() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        moviesAdapter = MoviesAdapter(this, null, glide,
            object : MovieItemClickListener {
                override fun onClick(context: Context?, movie: Movie) {
                    startActivity(MovieReviewActivity.createInstance(context, movie))
                    overridePendingTransition(R.anim.enter_from_bottom, R.anim.stay)
                }
            })
        binding.rvMovies.adapter = moviesAdapter
        binding.rvMovies.itemAnimator = DefaultItemAnimator()
        binding.rvMovies.layoutManager = GridLayoutManager(
            this, DEFAULT_GRID_SPAN_COUNT,
            GridLayoutManager.VERTICAL, false
        )
    }

    private fun getLoadingDialog(): Dialog {
        return Dialog(this, R.style.DialogFullScreen).apply {
            setContentView(R.layout.loading_processbar)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    override fun onMovieDataLoaded(tmdbResponse: TMDBResponse?) {
        if (tmdbResponse?.results != null) {
            moviesAdapter.addMovies(tmdbResponse.results)
            binding.rvMovies.visibility = View.VISIBLE
            binding.tvBroken.visibility = View.GONE
            binding.ivBroken.visibility = View.GONE
            binding.btnBroken.visibility = View.GONE
        } else {
            Logger.d("Movie Data Load : Failed")
            showErrorToast()
            binding.rvMovies.visibility = View.GONE
            binding.tvBroken.visibility = View.VISIBLE
            binding.ivBroken.visibility = View.VISIBLE
            binding.btnBroken.visibility = View.GONE
        }
    }

    private fun showErrorToast() {
        binding.tvError.visibility = View.VISIBLE
        showDelayed({ binding.tvError.visibility = View.GONE })
    }

    private fun showDelayed(runnable: Runnable, delay: Int = 1000) {
        Handler(Looper.getMainLooper()).postDelayed(runnable, delay.toLong())
    }

    override fun showLoader() {
        loadingDialog.show()
    }

    override fun hideLoader() {
        loadingDialog.hide()
    }
}