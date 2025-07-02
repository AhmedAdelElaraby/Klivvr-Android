package com.klivvr.klivvrandroid.domain.usecase

import com.klivvr.klivvrandroid.domain.model.City
import com.klivvr.klivvrandroid.domain.repository.CityRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend   fun getAllCities( ): List<City> {
        return repository.getAllCities()
    }
    suspend   fun Search(prefix: String): List<City> {
        return repository.searchCitiesByPrefix(prefix)
    }

}
