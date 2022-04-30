package com.heixss.myspendings.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Spending::class, Category::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun spendingDao(): SpendingDao
    abstract fun categoryDao(): CategoryDao
}
