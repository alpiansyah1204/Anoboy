package com.example.submission1.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.submission1.R
import com.example.submission1.core.data.Resource
import com.example.submission1.core.domain.model.Anime
import com.example.submission1.core.utils.loadImage
import com.example.submission1.databinding.ActivityDetailBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()
    private var statusFavorite = false
    private var anime: Anime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val animeId = intent.getStringExtra(EXTRA_ANIME_ID)
        detailanimePopulate(animeId)
        binding.fabFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            anime?.let {
                if (statusFavorite) {
                    detailViewModel.insertFavoriteAnime(it)
                    Toasty.success(this, "Added to Favorite", Toasty.LENGTH_SHORT).show()
                } else {
                    detailViewModel.deleteFavoriteAnime(it)
                    Toasty.error(this, "Removed from Favorite", Toasty.LENGTH_SHORT).show()
                }
                setFavoriteStatus(statusFavorite)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun detailanimePopulate(animeId: String?) {
        fabFavorite(false)
        animeId?.let { detailViewModel.setId(it) }

        detailViewModel.animeDetail.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { anime ->
                            binding.apply {
                                collapsingToolbarDetail.title = anime.canonicalTitle
                                imgPosterDetail.loadImage(anime.posterImage?.original)
                                imgCoverDetail.loadImage(anime.coverImage?.original)
                                tvPopularityDetail.text =
                                    getString(R.string.popularity_rank, anime.popularityRank)
                                tvFavoriteCountDetail.text =
                                    anime.favoritesCount?.toString() ?: "-"
                                tvEpisodeDetail.text =
                                    getString(R.string.episode_count, anime.episodeCount)
                                tvAverageRatingDetail.text = anime.averageRating ?: "-"
                                tvUserCountDetail.text = anime.userCount?.toString() ?: "-"
                                tvStatusDetail.text = anime.status ?: "-"
                                tvTitleEnJpDetail.text = anime.titles?.enJp ?: "-"
                                tvTitleJaJpDetail.text = anime.titles?.jaJp ?: "-"
                                tvSynopsisDetail.text = anime.synopsis ?: "-"
                            }
                            this.anime = anime


                            CoroutineScope(Dispatchers.IO).launch {
                                detailViewModel.isFavoriteAnime(anime.id)
                            }
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { error ->
                            Toasty.warning(this, error, Toasty.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    }
                }
            }
        }
        detailViewModel.isFavorite.observe(this@DetailActivity) {
            statusFavorite = it > 0
            setFavoriteStatus(statusFavorite)
            fabFavorite(true)
        }


    }

    private fun showLoading(state: Boolean) {
        binding.spinDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setFavoriteStatus(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_outline
                )
            )
        }
    }

    private fun fabFavorite(state: Boolean) {
        binding.fabFavorite.isEnabled = state
    }

    companion object {
        const val EXTRA_ANIME_ID = "extra_anime_id"
    }
}