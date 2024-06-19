package com.capstone.chilichecker.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookmarkDeleteResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
