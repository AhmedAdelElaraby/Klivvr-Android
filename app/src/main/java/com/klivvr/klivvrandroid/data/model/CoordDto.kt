package com.klivvr.klivvrandroid.data.model

import kotlinx.serialization.Serializable


@Serializable
data class CoordDto(
    val lon: Double,
    val lat: Double
)
