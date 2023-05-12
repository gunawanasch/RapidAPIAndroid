package com.gunawan.rapidapi.repositories.model

import com.google.gson.annotations.SerializedName

data class JSONResp(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
