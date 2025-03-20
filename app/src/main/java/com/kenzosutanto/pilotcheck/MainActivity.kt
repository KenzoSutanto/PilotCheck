package com.kenzosutanto.pilotcheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kenzosutanto.pilotcheck.ui.theme.PilotCheckTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PilotCheckTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "MetarScreenV2"
                ) {
                    composable(route = "MetarScreenV2") {
                        MetarScreenV2(navHostController = navController)
                    }
                    composable(route = "SettingsScreen") {
                        SettingsScreen(navHostController = navController)
                    }
                }

            }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Barometer?.toDisplayValueAndUnit(): Pair<String, String> {
   
    return when {
        this?.hpa != null -> this.hpa.toString() to "hPa"
        this?.hg != null -> this.hg.toString() to "inHg"
        else -> "N/A" to ""
    }
}

fun Dewpoint?.toDisplayValueAndUnit(): Pair<String, String> {
    return when {
        this?.celsius != null -> this.celsius.toString() to " °C"
        this?.fahrenheit != null -> this.fahrenheit.toString() to " °F"
        else -> "N/A" to ""
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetarScreenV2(navHostController: NavHostController) {
    val context = LocalContext.current
    val dataStore = remember { SettingsDataStore(context) }
    val isImperial by dataStore.isImperial.collectAsState(initial = false)

    val viewModel: MetarViewModel = viewModel()
    val metarData by viewModel.metarData.collectAsState()

    var icaoInput by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = { navHostController.navigate("SettingsScreen") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Settings")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = icaoInput,
                    onValueChange = { icaoInput = it },
                    label = { Text("Enter ICAO Code") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { viewModel.fetchMetar(icaoInput, isImperial) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("GET METAR")
                }
            }

            item {
                DataCard(
                    title = "Visibility",
                    value = if (isImperial)
                        "${metarData?.visibility?.miles ?: "N/A"} mi"
                    else
                        "${metarData?.visibility?.meters ?: "N/A"} m",
                    unit = if (isImperial) "mi" else "m"
                )
            }

            item {
                CloudTable(clouds = metarData?.clouds ?: emptyList())
            }
        }
    }
}




