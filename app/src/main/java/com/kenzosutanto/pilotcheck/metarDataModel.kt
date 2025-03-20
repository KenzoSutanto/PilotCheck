package com.kenzosutanto.pilotcheck

data class MetarResponse(
    val results: Int,
    val data: List<MetarData>
)

data class MetarData(
    val icao: String,
    val observed: String,
    val raw_text: String,
    val temperature: Temperature,
    val dewpoint: Dewpoint,
    val wind: Wind,
    val visibility: Visibility,
    val clouds: List<Cloud>,
    val station: Station,
    val barometer: Barometer,
    val flight_category: String
)


data class Station(val name: String) {
    override fun toString(): String {
        return name
    }
}
data class Temperature(val celsius: Int, val fahrenheit: Int)
data class Dewpoint(val celsius: Int, val fahrenheit: Int)
data class Wind(val degrees: Int, val speed_kts: Int)
data class Visibility(val meters: String, val miles: String)
data class Cloud(val code: String, val text: String, val base_feet_agl: Int)
data class Barometer(val hg: Double, val hpa: Double )

