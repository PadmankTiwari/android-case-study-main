package com.target.targetcasestudy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Deal(
  @Json(name = "id")
  val id: String? = null,
  @Json(name = "title")
  val title: String? = null,
  @Json(name = "aisle")
  val aisle: String? = null,
  @Json(name = "description")
  val description: String? = null,
  @Json(name = "image_url")
  val imageUrl: String? = null,
  @Json(name = "fulfillment")
  val fulfillment: String? = null,
  @Json(name = "availability")
  val availability: String? = null,
  @Json(name = "sale_price")
  val salePrice: Price? = null,
  @Json(name = "regular_price")
  val regularPrice: Price? = null,
)
