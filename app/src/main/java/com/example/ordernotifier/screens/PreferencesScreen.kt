package com.example.ordernotifier.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ordernotifier.data.DataStoreManager
import com.example.ordernotifier.ui.theme.ThemedScreen
import kotlinx.coroutines.launch

@Composable
fun PreferencesScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    val darkModeState = dataStoreManager.isDarkMode.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    val textColor = MaterialTheme.colorScheme.onBackground

    ThemedScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Preferences", style = MaterialTheme.typography.titleLarge, color = textColor)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Dark Mode",
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = darkModeState.value,
                    onCheckedChange = { isChecked ->
                        Log.d("DarkModeToggle", "Saving dark mode: $isChecked")
                        coroutineScope.launch {
                            dataStoreManager.saveDarkModeState(isChecked)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("main")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Return")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesScreenPreview() {
    val context = LocalContext.current
    PreferencesScreen(
        navController = rememberNavController(),
        dataStoreManager = DataStoreManager(context)
    )
}
