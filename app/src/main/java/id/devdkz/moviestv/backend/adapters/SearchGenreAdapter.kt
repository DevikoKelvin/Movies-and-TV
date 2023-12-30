package id.devdkz.moviestv.backend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.devdkz.moviestv.BuildConfig
import id.devdkz.moviestv.R
import id.devdkz.moviestv.backend.data.GenreListResponse
import id.devdkz.moviestv.backend.data.GenreListResultsItem
import id.devdkz.moviestv.backend.data.MovieTvReviewResultsItem
import id.devdkz.moviestv.databinding.ListViewBinding

class SearchGenreAdapter(val data: List<GenreListResultsItem>) :
    RecyclerView.Adapter<SearchGenreAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_view, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.apply {
                val item = data[position]

                Glide.with(holder.itemView.context)
                    .load(BuildConfig.IMG_URL + item.posterPath)
                    .into(imgShow)
                titleTxt.text = item.title
                synopsisTxt.text = item.overview

                itemView.setOnClickListener {
                    onItemClickListener.onItemClick(item)
                }
            }
        }
    }

    fun onItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(data: GenreListResultsItem)
    }
}