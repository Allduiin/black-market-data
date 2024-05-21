package com.cashir.blackmarketdata.api

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AlbionDataItem (
    val itemId: String,
    val city: String,
    val quality: Int,
    val sellPriceMin: Int
)
