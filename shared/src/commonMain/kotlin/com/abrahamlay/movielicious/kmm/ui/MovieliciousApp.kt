package com.abrahamlay.movielicious.kmm.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abrahamlay.movielicious.kmm.ui.theme.MovieliciousTheme

@Composable
fun MovieliciousApp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    MovieliciousTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}