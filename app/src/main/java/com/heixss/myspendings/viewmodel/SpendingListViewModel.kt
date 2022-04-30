package com.heixss.myspendings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.myspendings.database.Category
import com.heixss.myspendings.repository.MainRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SpendingListViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var categoryId: Long = 0
    var month: Int = 0
    var year: Int = 0

    val liveTotalCategorySpendings = MutableLiveData<List<TotalCategorySpendings>>()

    fun loadTotalCategorySpendings(month: Int, year: Int) {
        viewModelScope.launch {
            val list = ArrayList<TotalCategorySpendings>()
            val categories = mainRepository.getAllCategories()
            categories.forEach { category ->
                var totalSum = 0.0
                val spendings = mainRepository.getSpendingsByCategoryId(category.id, month, year)
                spendings.forEach { spending ->
                    totalSum += spending.value
                }
                if (totalSum > 0)
                    list.add(TotalCategorySpendings(category, totalSum))
            }
            list.sortByDescending { totalCategorySpendings -> totalCategorySpendings.totalSum }
            liveTotalCategorySpendings.value = list
        }
    }
}

class TotalCategorySpendings(val category: Category, val totalSum: Double)
