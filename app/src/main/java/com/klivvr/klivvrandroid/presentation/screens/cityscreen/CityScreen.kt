package com.klivvr.klivvrandroid.presentation.screens.cityscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.klivvr.klivvrandroid.domain.model.City
import com.klivvr.klivvrandroid.ui.cityuistate.CityUiState
import androidx.compose.ui.platform.LocalContext



@Composable
fun CityScreen(viewModel: CityViewModel = hiltViewModel()) {
    val state = viewModel.state
    val query = viewModel.query

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        AnimatedSearchBar(query = query, onQueryChange = viewModel::onQueryChanged)

        when (state) {
            is CityUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is CityUiState.Empty -> Text("No cities found", modifier = Modifier.align(Alignment.CenterHorizontally))
            is CityUiState.Success -> CityList(cities = state.cities)
        }
    }
}


@Composable
fun CityList(cities: List<City>) {
    val grouped = cities.groupBy { it.name.first().uppercaseChar() }
    LazyColumn {
        grouped.forEach { (char, list) ->
            stickyHeader {
                Text(text = char.toString(), modifier = Modifier.background(Color.LightGray).fillMaxWidth().padding(8.dp))
            }
            items(list) { city ->
                CityItem(city)
            }
        }
    }
}


@Composable
fun CityItem(city: City) {
    val context = LocalContext.current // ✅ خده هنا فوق

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val uri = Uri.parse("geo:${city.lat},${city.lon}")
                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage("com.google.android.apps.maps")
                }
                context.startActivity(intent) // ✅ استخدمه هنا
            }
            .padding(16.dp)
    ) {
        Text("${city.name}, ${city.country}", fontWeight = FontWeight.Bold)
        Text("Lat: ${city.lat}, Lon: ${city.lon}")
    }
}




@Composable
fun AnimatedSearchBar(query: String, onQueryChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val focusState = remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = focusState.value, label = "FocusTransition")
    val backgroundColor by transition.animateColor(label = "ColorAnim") {
        if (it) Color.LightGray else Color.White
    }

    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search city") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .focusRequester(focusRequester)
            .onFocusChanged { focusState.value = it.isFocused }
    )
}








