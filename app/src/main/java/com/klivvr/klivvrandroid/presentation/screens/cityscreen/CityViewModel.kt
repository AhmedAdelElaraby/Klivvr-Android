package com.klivvr.klivvrandroid.presentation.screens.cityscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klivvr.klivvrandroid.domain.usecase.SearchCitiesUseCase
import com.klivvr.klivvrandroid.ui.cityuistate.CityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase
) : ViewModel() {

    var query by mutableStateOf("")
    var state by mutableStateOf<CityUiState>(CityUiState.Loading)


    init {
        getAllCities()
    }

    fun onQueryChanged(input: String) {
        query = input
        viewModelScope.launch {
            state = CityUiState.Loading
            val result = searchCitiesUseCase.Search(input)
            state = if (result.isEmpty()) CityUiState.Empty else CityUiState.Success(result)
        }
    }


    fun getAllCities(){

      viewModelScope.launch {
          state = CityUiState.Loading
          val city = searchCitiesUseCase.getAllCities()
          state = if (city.isEmpty()) CityUiState.Empty else CityUiState.Success(city)


      }
    }
}
