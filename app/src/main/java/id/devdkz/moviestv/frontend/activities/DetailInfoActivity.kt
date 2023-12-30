package id.devdkz.moviestv.frontend.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.devdkz.moviestv.BuildConfig
import id.devdkz.moviestv.R
import id.devdkz.moviestv.backend.adapters.ReviewsAdapter
import id.devdkz.moviestv.backend.data.MovieDetailResponse
import id.devdkz.moviestv.backend.data.MovieTvReviewResponse
import id.devdkz.moviestv.backend.data.TvDetailResponse
import id.devdkz.moviestv.backend.dataconvert.Converter.movieDetailResponseToBookmarkEntity
import id.devdkz.moviestv.backend.dataconvert.Converter.tvDetailResponseToBookmarkEntity
import id.devdkz.moviestv.backend.repos.cloud.api.APIConf
import id.devdkz.moviestv.backend.viewmodel.FactoryViewModel
import id.devdkz.moviestv.backend.viewmodel.MainViewModel
import id.devdkz.moviestv.databinding.ActivityDetailInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailInfoBinding
    private lateinit var vm: MainViewModel
    private lateinit var reviewAdapter: ReviewsAdapter
    private var togChecker: Boolean = false

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TYPE = "EXTRA_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this,
            FactoryViewModel.getInstance(this)
        )[MainViewModel::class.java]

        val id = intent.getIntExtra(EXTRA_ID, 0)
        Log.d("Detailed Data ID", id.toString())
        val cat = intent.getStringExtra(EXTRA_TYPE)
        Log.d("Detailed Data Category", cat.toString())

        showLoading(true)

        binding.apply {
            id.let {
                if (cat != null) {
                    if (cat == "movie") {
                        APIConf.getAPI().getMovieDetail(id).enqueue(
                            object : Callback<MovieDetailResponse> {
                                override fun onResponse(
                                    call: Call<MovieDetailResponse>,
                                    response: Response<MovieDetailResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        if (response.body() != null) {
                                            showLoading(false)
                                            castMovieData(response.body()!!)
                                            bookmarkBtn.setOnClickListener {
                                                if (!togChecker) {
                                                    vm.insertToBookmark(
                                                        response.body()!!
                                                            .movieDetailResponseToBookmarkEntity(cat)
                                                    )
                                                    Toast.makeText(
                                                        this@DetailInfoActivity,
                                                        getString(R.string.bookmarked),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    vm.deleteFromBookmark(
                                                        response.body()!!
                                                            .movieDetailResponseToBookmarkEntity(cat)
                                                    )
                                                    Toast.makeText(
                                                        this@DetailInfoActivity,
                                                        getString(R.string.un_bookmarked),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                                bookmarkBtn.isChecked = !togChecker
                                            }

                                            CoroutineScope(Dispatchers.IO).launch {
                                                val i = vm.checkBookmark(id)
                                                withContext(Dispatchers.Main) {
                                                    if (i > 0) {
                                                        bookmarkBtn.isChecked = true
                                                        togChecker = true
                                                    } else {
                                                        bookmarkBtn.isChecked = false
                                                        togChecker = false
                                                    }
                                                }
                                            }

                                            vm.getBookmarkDetail(response.body()!!.id!!, cat)
                                                .observe(this@DetailInfoActivity) { bookmark ->
                                                    bookmark?.let {
                                                        togChecker = true
                                                        bookmarkBtn.isChecked = true
                                                    }
                                                }
                                            Log.e("Movie Response", response.body().toString())
                                        }
                                    }
                                }

                                override fun onFailure(
                                    call: Call<MovieDetailResponse>,
                                    t: Throwable
                                ) {
                                    t.printStackTrace()
                                }
                            }
                        )
                    } else {
                        APIConf.getAPI().getTvDetail(id).enqueue(
                            object : Callback<TvDetailResponse> {
                                override fun onResponse(
                                    call: Call<TvDetailResponse>,
                                    response: Response<TvDetailResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        if (response.body() != null) {
                                            showLoading(false)
                                            castTvData(response.body()!!)
                                            bookmarkBtn.setOnClickListener {
                                                if (!togChecker) {
                                                    vm.insertToBookmark(
                                                        response.body()!!
                                                            .tvDetailResponseToBookmarkEntity(cat)
                                                    )
                                                    Toast.makeText(
                                                        this@DetailInfoActivity,
                                                        getString(R.string.bookmarked),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    vm.deleteFromBookmark(
                                                        response.body()!!
                                                            .tvDetailResponseToBookmarkEntity(cat)
                                                    )
                                                    Toast.makeText(
                                                        this@DetailInfoActivity,
                                                        getString(R.string.un_bookmarked),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                                bookmarkBtn.isChecked = !togChecker
                                            }

                                            CoroutineScope(Dispatchers.IO).launch {
                                                val i = vm.checkBookmark(id)
                                                withContext(Dispatchers.Main) {
                                                    if (i > 0) {
                                                        bookmarkBtn.isChecked = true
                                                        togChecker = true
                                                    } else {
                                                        bookmarkBtn.isChecked = false
                                                        togChecker = false
                                                    }
                                                }
                                            }

                                            vm.getBookmarkDetail(response.body()!!.id!!, cat)
                                                .observe(this@DetailInfoActivity) { bookmark ->
                                                    bookmark?.let {
                                                        togChecker = true
                                                        bookmarkBtn.isChecked = true
                                                    }
                                                }
                                            Log.e("TV Response", response.body().toString())
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                                    t.printStackTrace()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun castMovieData(data: MovieDetailResponse) = binding.apply {
        Glide.with(applicationContext)
            .load(BuildConfig.IMG_URL + data.posterPath)
            .into(imgView)
        titleTxt.text = data.title
        ratingTxt.text = data.voteAverage.toString()
        reldateTxt.text = data.releaseDate
        synTxt.text = data.overview
        showReviews("movie", data.id!!)
    }

    private fun castTvData(data: TvDetailResponse) = binding.apply {
        Glide.with(applicationContext)
            .load(BuildConfig.IMG_URL + data.posterPath)
            .into(imgView)
        titleTxt.text = data.name
        ratingTxt.text = data.voteAverage.toString()
        reldateTxt.text = data.firstAirDate
        synTxt.text = data.overview
        showReviews("tv", data.id!!)
    }

    private fun showReviews(category: String, id: Int) = binding.apply {
        APIConf.getAPI().getMovieTvReviews(category, id).enqueue(
            object : Callback<MovieTvReviewResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<MovieTvReviewResponse>,
                    response: Response<MovieTvReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            reviewAdapter = ReviewsAdapter(response.body()!!.results)
                            reviewRv.layoutManager = LinearLayoutManager(this@DetailInfoActivity)
                            reviewRv.adapter = reviewAdapter
                            reviewAdapter.notifyDataSetChanged()
                            Log.e("Response Review", response.body().toString())
                        }
                    }
                }

                override fun onFailure(call: Call<MovieTvReviewResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    private fun showLoading(cond: Boolean) =
        binding.apply {
            if (cond)
                loadingBar.visibility = View.VISIBLE
            else
                loadingBar.visibility = View.GONE
        }
}