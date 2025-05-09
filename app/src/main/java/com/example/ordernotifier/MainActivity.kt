package com.example.ordernotifier

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.ordernotifier.screens.MainScreen
import com.example.ordernotifier.screens.PreferencesScreen
import com.example.ordernotifier.ui.theme.OrderNotifierTheme
import com.example.ordernotifier.data.DataStoreManager
import com.example.ordernotifier.models.OrderViewModel
import com.example.ordernotifier.models.OrderViewModelFactory
import com.example.ordernotifier.screens.HelpScreen
import com.example.ordernotifier.screens.RegistrationScreen
import android.os.Build
import androidx.core.content.ContextCompat


class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }


        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)

        setContent {
            val isDarkMode by dataStoreManager.isDarkMode.collectAsState(initial = false)

            OrderNotifierTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val orderViewModel: OrderViewModel = viewModel(
                    factory = OrderViewModelFactory(applicationContext)
                )

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController, orderViewModel)
                    }
                    composable("preferences") {
                        PreferencesScreen(navController, dataStoreManager)
                    }
                    composable("help") {
                        HelpScreen(navController)
                    }
                    composable("secondary") {
                        RegistrationScreen(navController, orderViewModel)
                    }
                }

            }
        }
    }
}
