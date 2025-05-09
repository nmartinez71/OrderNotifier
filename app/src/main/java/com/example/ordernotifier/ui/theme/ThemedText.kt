package com.example.ordernotifier.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.material3.Text

@Composable
fun ThemedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    textAlign: TextAlign? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = style,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = fontSize
    )
}
