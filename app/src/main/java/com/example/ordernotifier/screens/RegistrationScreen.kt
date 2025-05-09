package com.example.ordernotifier.screens


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ordernotifier.models.OrderViewModel
import com.example.ordernotifier.ui.theme.ThemedScreen

@SuppressLint("MissingPermission")
@Composable
fun RegistrationScreen(navController: NavController, viewModel: OrderViewModel) {
    var orderNumber by remember { mutableStateOf(TextFieldValue()) }
    val validInputRegex = "^[0-9l]{1,9}$".toRegex()
    var isValid by remember { mutableStateOf(true) }
    val context = LocalContext.current

    ThemedScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Enter Order Number",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            TextField(
                value = orderNumber,
                onValueChange = { newText ->
                    orderNumber = newText
                    isValid = newText.text.matches(validInputRegex)
                },
                label = { Text("Order Number") },
                modifier = Modifier.fillMaxWidth(),
                isError = !isValid && orderNumber.text.isNotBlank()
            )


            if (!isValid && orderNumber.text.isNotBlank()) {
                Text(
                    text = "Invalid order number. Only numbers allowed (up to 9 digits).",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    if (orderNumber.text.isNotBlank() && isValid) {
                        viewModel.addOrder(orderNumber.text.toInt().toString(), context)
                        navController.navigate("main")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isValid
            ) {
                Text("Queue Order")
            }

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.popBackStack()
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
fun RegistrationScreenPreview() {
    val dummyViewModel = object : OrderViewModel() {
        override fun addOrder(orderNumber: String, context: Context) {
        }
    }
    RegistrationScreen(navController = rememberNavController(), viewModel = dummyViewModel)
}



