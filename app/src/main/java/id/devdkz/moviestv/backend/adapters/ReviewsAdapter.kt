package id.devdkz.moviestv.backend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.devdkz.moviestv.BuildConfig
import id.devdkz.moviestv.R
import id.devdkz.moviestv.backend.data.MovieTvReviewResponse
import id.devdkz.moviestv.backend.data.MovieTvReviewResultsItem
import id.devdkz.moviestv.databinding.ReviewListItemBinding

class ReviewsAdapter(val data: List<MovieTvReviewResultsItem>) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ReviewListItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            binding.apply {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMG_URL + item.authorDetails.avatarPath)
                    .into(avatarView)
                authorName.text = item.authorDetails.name
                authorUsername.text = item.authorDetails.username
                reviewContent.text = item.content
                authorRating.text = item.authorDetails.rating.toString()
            }
        }
    }
}