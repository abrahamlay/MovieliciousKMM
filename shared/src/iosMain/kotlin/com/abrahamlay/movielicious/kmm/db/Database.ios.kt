package com.abrahamlay.movielicious.kmm.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

// Android implementation
//actual class DriverFactory() {
//    actual val databaseName = "movieliciouskmm.db"
//    actual fun createDriver(): SqlDriver {
//        return NativeSqliteDriver(AppDatabase.Schema, databaseName)
//    }
//}