package org.abrahamlay.movielicious.kmm.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.abrahamlay.movielicious.kmm.theme.MovieliciousTheme

@Composable
@Preview
fun App() {
    MovieliciousTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            HomeComponent()
        }
    }
}