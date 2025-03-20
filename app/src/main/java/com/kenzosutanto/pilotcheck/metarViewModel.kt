package com.kenzosutanto.pilotcheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetarViewModel : ViewModel() {
    fun MetarData.formatForUnits(isImperial: Boolean): MetarData {
        return if (isImperial) {
            this.copy(
                temperature = this.temperature.copy(celsius = (this.temperature.celsius * 9 / 5) + 32),
                dewpoint = this.dewpoint.copy(celsius = (this.dewpoint.celsius * 9 / 5) + 32),
                visibility = this.visibility.copy(meters = this.visibility.miles),
                barometer = this.barometer.copy(hpa = this.barometer.hg)
            )
        } else {
            this
        }
    }

    fun fetchMetar(icao: String, isImperial: Boolean) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMetar(icao, apiKey)
                if (response.results > 0) {
                    _metarData.value = response.data.firstOrNull()?.formatForUnits(isImperial)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




    private val _metarData = MutableStateFlow<MetarData?>(null)
    val metarData: StateFlow<MetarData?> get() = _metarData

    private val apiKey = "a1feb53635084e7496d6f27cb1"


}
