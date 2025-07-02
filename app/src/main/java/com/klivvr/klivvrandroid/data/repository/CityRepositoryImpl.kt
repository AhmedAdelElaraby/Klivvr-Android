package com.klivvr.klivvrandroid.data.repository

import android.content.Context
import com.klivvr.klivvrandroid.data.model.CityDto
import com.klivvr.klivvrandroid.domain.model.City
import com.klivvr.klivvrandroid.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class CityRepositoryImpl(
    private val context: Context
) : CityRepository {

    private val prefixMap: MutableMap<String, MutableList<City>> = mutableMapOf()

    init {
        runBlocking {
            preprocessCities()
        }
    }

    private suspend fun preprocessCities() {
        withContext(Dispatchers.IO) {
            val json = context.assets.open("cities.json").bufferedReader().use { it.readText() }
            val cityDtos = Json.decodeFromString<List<CityDto>>(json)
            cityDtos.map { dto ->
                City(
                    id = dto._id,
                    name = dto.name,
                    country = dto.country,
                    lat = dto.coord.lat,
                    lon = dto.coord.lon
                )
            }.forEach { city ->
                val lowerName = city.name.lowercase()
                for (i in 1..lowerName.length) {
                    val prefix = lowerName.substring(0, i)
                    prefixMap.getOrPut(prefix) { mutableListOf() }.add(city)
                }
            }
        }
    }

    override suspend fun getAllCities(): List<City> = prefixMap.values.flatten().distinct().sortedBy { it.name + it.country }

    override suspend fun searchCitiesByPrefix(prefix: String): List<City> {
        return prefixMap[prefix.lowercase()].orEmpty().sortedBy { it.name + it.country }
    }
}
