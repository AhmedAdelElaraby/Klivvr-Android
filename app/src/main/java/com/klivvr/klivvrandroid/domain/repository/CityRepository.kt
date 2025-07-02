package com.klivvr.klivvrandroid.domain.repository

import com.klivvr.klivvrandroid.domain.model.City

interface CityRepository {
    suspend fun getAllCities(): List<City>
    suspend fun searchCitiesByPrefix(prefix: String): List<City>
}