package com.capstone.chilichecker.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookmarkListResponse(

	@field:SerializedName("totalItems")
	val totalItems: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("marketPrice")
	val marketPrice: String? = null,

	@field:SerializedName("careInstructions")
	val careInstructions: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("suitableDishes")
	val suitableDishes: String? = null,

	@field:SerializedName("idPredict")
	val idPredict: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)
