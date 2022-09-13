package com.example.submission1.core.data.remote.response

data class DataResponse(
    val id: String?,
    val attributes: AttributesResponse?,
)
data class AttributesResponse(
    val synopsis: String?,
    val titles: TitlesResponse?,
    val canonicalTitle: String?,
    val averageRating: String?,
    val userCount: Int?,
    val favoritesCount: Int?,
    val popularityRank: Int?,
    val ratingRank: Int?,
    val status: String?,
    val posterImage: PosterImageResponse?,
    val coverImage: CoverImageResponse?,
    val episodeCount: Int?,
    val youtubeVideoId: String?,
    val showType: String?,
)
data class PosterImageResponse(
    val medium: String?,
    val large: String?,
    val original: String?,
)
data class CoverImageResponse(
    val small: String?,
    val large: String?,
    val original: String?,
)
