package com.abrahamlay.movielicious.kmm.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.abrahamlay.movielicious.kmm.ui.screens.home.HomeScreen

fun MainViewController() = ComposeUIViewController {
    MovieliciousApp {
        HomeScreen()
    }
}