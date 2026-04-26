package com.abrahamlay.movielicious.kmm.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual val databaseName = "movieliciouskmm.db"

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = AppDatabase.Schema,
            databaseName = databaseName
        )
    }
}