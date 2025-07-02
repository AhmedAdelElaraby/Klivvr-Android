package com.klivvr.klivvrandroid.domain.model

import kotlinx.serialization.Serializable

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
)