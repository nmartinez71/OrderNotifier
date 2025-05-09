package com.example.ordernotifier.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ordernotifier.data.OrderInfo
import com.example.ordernotifier.models.OrderViewModel
import com.example.ordernotifier.ui.theme.ThemedScreen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, viewModel: OrderViewModel) {
    val orders = viewModel.orders


    ThemedScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Waiting List",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            orders.forEach { order ->
                OrderItem(order = order) {
                    viewModel.removeOrder(order)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("secondary") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registration")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("preferences") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Preferences")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("help") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Help")
            }
        }
    }
}

@Composable
fun OrderItem(order: OrderInfo, onRemove: () -> Unit) {
    var timeLeft by remember { mutableStateOf(order.timeRemaining) }

    LaunchedEffect(order.orderNumber) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft = order.timeRemaining
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (timeLeft > 0)
                "${order.orderNumber} : ${timeLeft}s remaining"
            else
                "${order.orderNumber} : OVERDUE",
            style = MaterialTheme.typography.bodyLarge,
            color = if (timeLeft > 0)
                MaterialTheme.colorScheme.onBackground
            else
                MaterialTheme.colorScheme.error
        )
        Button(onClick = onRemove) {
            Text("Remove")
        }
    }
}



@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(viewModel = OrderViewModel(), navController = rememberNavController())
}
