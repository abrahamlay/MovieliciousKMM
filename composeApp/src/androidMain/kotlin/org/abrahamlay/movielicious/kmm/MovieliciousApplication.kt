package org.abrahamlay.movielicious.kmm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp(Application::class)
class MovieliciousApplication: Hilt_MovieliciousApplication() {
}