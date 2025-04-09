package com.example.airtrack.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Text

val LocalErrorHandler = compositionLocalOf<((Throwable) -> Unit)> {
    { throw it } // Default handler
}

@Composable
fun ErrorBoundary(
    content: @Composable () -> Unit
) {
    val error = remember { mutableStateOf<Throwable?>(null) }

    if (error.value != null) {
        Text("Error: ${error.value?.localizedMessage.orEmpty()}")
    } else {
        CompositionLocalProvider(
            LocalErrorHandler provides { throwable ->
                error.value = throwable
            }
        ) {
            content()
        }
    }
}