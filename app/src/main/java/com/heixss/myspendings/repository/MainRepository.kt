package com.heixss.myspendings.repository

import com.heixss.myspendings.database.AppDatabase
import com.heixss.myspendings.database.Category
import com.heixss.myspendings.database.Spending
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    suspend fun getAllCategories(): List<Category> {
        return appDatabase.categoryDao().getAllCategories()
    }

    suspend fun insertCategory(category: Category) {
        appDatabase.categoryDao().insert(category)
    }

    suspend fun insertSpending(spending: Spending) {
        appDatabase.spendingDao().insert(spending)
    }

    suspend fun getCategory(category: String): Category? {
        return appDatabase.categoryDao().getCategory(category)
    }

    suspend fun getCategoryById(categoryId: Long): Category {
        return appDatabase.categoryDao().getCategoryById(categoryId)
    }

    suspend fun removeSpending(spendingId: Long) {
        appDatabase.spendingDao().deleteSpending(spendingId)
    }

    suspend fun clearSpendings() {
        appDatabase.spendingDao().clearAll()
    }

    fun liveSpendings() = appDatabase.spendingDao().liveSpendings()

    fun liveSpendings(month: Int, year: Int) = appDatabase.spendingDao().liveSpendings(month, year)

    suspend fun getSpendings(month: Int, year: Int) =
        appDatabase.spendingDao().getSpendings(month, year)

    suspend fun getSpendingsByCategoryId(categoryId: Long, month: Int, year: Int): List<Spending>{
        return appDatabase.spendingDao().getSpendingsByCategoryId(categoryId, month, year)
    }
}