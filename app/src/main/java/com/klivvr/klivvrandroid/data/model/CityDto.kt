package com.klivvr.klivvrandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val country: String,
    val name: String,
    val _id: Long,
    val coord: CoordDto
)
