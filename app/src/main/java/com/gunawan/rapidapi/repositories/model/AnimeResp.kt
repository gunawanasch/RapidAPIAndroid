package com.gunawan.rapidapi.repositories.model

import com.google.gson.annotations.SerializedName

data class AnimeResp(

	@field:SerializedName("data")
	val data: List<DataItemAnime?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class DataItemAnime(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("thumb")
	val thumb: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("synopsis")
	val synopsis: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("hasRanking")
	val hasRanking: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("hasEpisode")
	val hasEpisode: Boolean? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("ranking")
	val ranking: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("episodes")
	val episodes: Int? = null,

	@field:SerializedName("alternativeTitles")
	val alternativeTitles: List<String?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Meta(

	@field:SerializedName("totalData")
	val totalData: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("totalPage")
	val totalPage: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)
