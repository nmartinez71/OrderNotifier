package com.example.ordernotifier.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ordernotifier.ui.theme.ThemedScreen
import com.example.ordernotifier.R
import com.example.ordernotifier.ui.theme.ThemedText


@Composable
fun HelpScreen(navController: NavController) {
    ThemedScreen {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("About", style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground)

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                ThemedText(
                    text = stringResource(id = R.string.about_text)
                    )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("main") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Return")
            }
        }
    }
}