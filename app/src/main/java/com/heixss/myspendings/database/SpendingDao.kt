package com.heixss.myspendings.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
abstract class SpendingDao : BaseDao<Spending>() {

    @Query("SELECT * FROM spending ORDER BY value DESC")
    abstract fun liveSpendings(): LiveData<List<Spending>>

    @Query("SELECT * FROM spending WHERE month = :month AND year = :year ORDER BY value DESC")
    abstract fun liveSpendings(month: Int, year: Int): LiveData<List<Spending>>

    @Query("SELECT * FROM spending WHERE month = :month AND year = :year ORDER BY value DESC")
    abstract suspend fun getSpendings(month: Int, year: Int): List<Spending>

    @Query("SELECT * FROM spending WHERE categoryId = :categoryId AND month = :month AND year =:year ORDER BY value DESC")
    abstract suspend fun getSpendingsByCategoryId(categoryId: Long, month: Int, year: Int): List<Spending>

    @Query("DELETE FROM spending WHERE id = :spendingId")
    abstract suspend fun deleteSpending(spendingId: Long)

    @Query("DELETE FROM spending")
    abstract suspend fun clearAll()
}