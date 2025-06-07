package com.abrahamlay.movielicious.kmm.db
//
//import app.cash.sqldelight.db.SqlDriver
//import com.abrahamlay.movielicious.kmm.AppDatabase
//
//expect class DriverFactory {
//    val databaseName: String
//    fun createDriver(): SqlDriver
//}
//
//fun createDatabase(driverFactory: DriverFactory): AppDatabase {
//    val driver = driverFactory.createDriver()
//    val database = AppDatabase(driver)
//
//    // Do more work with the database (see below).
//}
//
//val userQueries: UserQueries = database.userQueries