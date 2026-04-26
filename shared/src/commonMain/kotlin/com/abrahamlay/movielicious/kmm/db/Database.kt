package com.abrahamlay.movielicious.kmm.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    val databaseName: String
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    return AppDatabase(driver)
}
