package com.abrahamlay.movielicious.kmm.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

// Android implementation
//actual class DriverFactory(private val context: Context) {
//    actual val databaseName = "movieliciouskmm.db"
//    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(AppDatabase.Schema, context, databaseName)
//    }
//}