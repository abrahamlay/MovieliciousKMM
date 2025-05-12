package org.abrahamlay.movielicious.kmm.component

import Greeting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import movieliciouskmm.composeapp.generated.resources.Res
import movieliciouskmm.composeapp.generated.resources.compose_multiplatform
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