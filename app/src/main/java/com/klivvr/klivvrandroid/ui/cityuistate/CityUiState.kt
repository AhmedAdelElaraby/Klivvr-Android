package com.klivvr.klivvrandroid.ui.cityuistate

import com.klivvr.klivvrandroid.domain.model.City

sealed class CityUiState {
    object Loading : CityUiState()
    object Empty : CityUiState()
    data class Success(val cities: List<City>) : CityUiState()
}