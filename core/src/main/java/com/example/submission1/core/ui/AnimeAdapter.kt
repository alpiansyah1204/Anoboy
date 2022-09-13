package com.example.submission1.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1.core.databinding.ItemAnimeBinding
import com.example.submission1.core.domain.model.Anime
import com.example.submission1.core.utils.loadImage

class AnimeAdapter(private val callback: AnimeCallback) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private val mData = ArrayList<Anime>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Anime>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    interface AnimeCallback {
        fun onAnimeClick(anime: Anime)
    }

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            with(binding) {
                tvTitleItem.text = anime.canonicalTitle ?: "-"
                tvSinopsis.text = anime.synopsis ?: "-"
                var rating = anime.averageRating?.toFloat()
                if (rating != null) {
                    rating /= 20
                }
                tvAverageRatingDetail.rating = rating ?: 0.0F
                tvAverageRatingDetailText.text =  anime.averageRating.toString()
                tvEpisodeDetail.text = anime.episodeCount.toString()
                tvStatusDetail.text = anime.status
                imgPosterItem.loadImage(anime.posterImage?.large)
                root.setOnClickListener { callback.onAnimeClick(anime) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnimeViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) =
        holder.bind(mData[position])

    override fun getItemCount(): Int = mData.count()

}