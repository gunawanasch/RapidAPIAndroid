package com.gunawan.rapidapi.repositories.model

import com.google.gson.annotations.SerializedName

data class GenreResp(

	@field:SerializedName("_id")
	val id: String? = null
) {
	override fun toString(): String {
		return id ?: ""
	}
}
