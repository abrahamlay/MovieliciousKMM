package org.abrahamlay.movielicious.kmm

import android.app.Application
import com.abrahamlay.movielicious.kmm.movie.di.appModule
import org.abrahamlay.movielicious.kmm.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(appModule() + androidModule)
        }

    }
}