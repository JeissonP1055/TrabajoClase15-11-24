package com.example.trabajoclase15_11_24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trabajoclase15_11_24.ui.theme.Trabajoclase15_11_24Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Trabajoclase15_11_24Theme {
                SettingsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var fontSize by remember { mutableStateOf(16f) }
    var selectedTheme by remember { mutableStateOf("Light") }
    val themeOptions = listOf("Light", "Dark", "System")

    // SnackbarState
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("Settings") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Acción de guardar
                saveSettings(
                    notificationsEnabled,
                    darkModeEnabled,
                    fontSize,
                    selectedTheme,
                    snackbarHostState,
                    coroutineScope
                )
            }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                SettingItem(
                    title = "Notifications",
                    description = "Enable or disable notifications",
                    control = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }
                )
            }
            item {
                SettingItem(
                    title = "Dark Mode",
                    description = "Switch between light and dark themes",
                    control = {
                        Switch(
                            checked = darkModeEnabled,
                            onCheckedChange = { darkModeEnabled = it }
                        )
                    }
                )
            }
            item {
                SettingItem(
                    title = "Font Size",
                    description = "Adjust the font size",
                    control = {
                        Slider(
                            value = fontSize,
                            onValueChange = { fontSize = it },
                            valueRange = 12f..24f
                        )
                    }
                )
            }
            item {
                SettingItem(
                    title = "Theme",
                    description = "Select the app theme",
                    control = {
                        var expanded by remember { mutableStateOf(false) }
                        OutlinedButton(onClick = { expanded = true }) {
                            Text(selectedTheme)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            themeOptions.forEach { theme ->
                                DropdownMenuItem(onClick = {
                                    selectedTheme = theme
                                    expanded = false
                                }) {
                                    Text(theme)
                                }
                            }
                        }
                    }
                )
            }
            item {
                SettingItem(
                    title = "Profile Picture",
                    description = "Change your profile picture",
                    control = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                )
            }
        }
    }
}

fun saveSettings(
    notificationsEnabled: Boolean,
    darkModeEnabled: Boolean,
    fontSize: Float,
    selectedTheme: String,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    // Simulación de guardado con un Snackbar
    val message = "Settings saved: Notifications=$notificationsEnabled, DarkMode=$darkModeEnabled, FontSize=$fontSize, Theme=$selectedTheme"
    coroutineScope.launch {
        snackbarHostState.showSnackbar(message)
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String,
    control: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                control()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Trabajoclase15_11_24Theme { SettingsScreen() }
}
