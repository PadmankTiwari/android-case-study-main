package com.target.targetcasestudy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
  @Json(name = "amount_in_cents")
  val amountInCents: Int? = null,
  @Json(name = "currency_symbol")
  val currencySymbol: String? = null,
  @Json(name = "display_string")
  val displayString: String? = null,
)
