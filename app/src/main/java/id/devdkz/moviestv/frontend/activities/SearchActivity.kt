package id.devdkz.moviestv.frontend.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.devdkz.moviestv.R
import id.devdkz.moviestv.backend.adapters.SearchGenreAdapter
import id.devdkz.moviestv.backend.data.GenreListResponse
import id.devdkz.moviestv.backend.data.GenreListResultsItem
import id.devdkz.moviestv.backend.data.MovieTvReviewResultsItem
import id.devdkz.moviestv.backend.repos.cloud.api.APIConf
import id.devdkz.moviestv.backend.viewmodel.FactoryViewModel
import id.devdkz.moviestv.backend.viewmodel.MainViewModel
import id.devdkz.moviestv.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), SearchGenreAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var selectedItem: String
    private lateinit var rvAdapter: SearchGenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            val viewModel = ViewModelProvider(
                this@SearchActivity,
                FactoryViewModel.getInstance(this@SearchActivity)
            )[MainViewModel::class.java]

            viewModel.getMovieGenre().observe(this@SearchActivity) { data ->
                val result = ArrayList<String>()
                result.add(
                    getString(R.string.select_genre)
                )
                for (res in 0 until data.size) {
                    data[res]?.let {
                        result.add(
                            it.name
                        )
                    }
                }
                val adapter = ArrayAdapter(
                    this@SearchActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    result
                )

                genreDropdown.adapter = adapter

                genreDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedItem = parent?.getItemAtPosition(position).toString()
                        if (selectedItem == getString(R.string.select_genre)) {
                            selectedItem = ""
                        }
                        Log.e("Selected Items", selectedItem)
                        resultRv.layoutManager = LinearLayoutManager(this@SearchActivity)
                        APIConf.getAPI().getGenreList(selectedItem).enqueue(
                            object : Callback<GenreListResponse> {
                                override fun onResponse(
                                    call: Call<GenreListResponse>,
                                    response: Response<GenreListResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        if (response.body() != null) {
                                            rvAdapter = SearchGenreAdapter(response.body()!!.results)
                                            resultRv.adapter = rvAdapter
                                            rvAdapter.notifyDataSetChanged()
                                            rvAdapter.onItemClickListener(this@SearchActivity)
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                                    t.printStackTrace()
                                }
                            }
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectedItem = ""
                        Log.e("Selected Items", selectedItem)
                    }
                }
            }
        }
    }

    override fun onItemClick(data: GenreListResultsItem) {
        startActivity(
            Intent(
                this@SearchActivity,
                DetailInfoActivity::class.java
            ).also {
                it.putExtra(DetailInfoActivity.EXTRA_TYPE, "movie")
                it.putExtra(DetailInfoActivity.EXTRA_ID, data.id)
            }
        )
    }
}