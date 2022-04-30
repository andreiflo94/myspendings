package com.heixss.myspendings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.myspendings.database.Category
import com.heixss.myspendings.database.Spending
import com.heixss.myspendings.repository.MainRepository
import kotlinx.coroutines.launch

class DetailedSpendingsViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    var categoryId: Long = 0
    var month: Int = 0
    var year: Int = 0

    val liveVMSpendings = MutableLiveData<List<VMSpending>>()
    fun loadVMSpendings(categoryId: Long, month: Int, year: Int) {
        viewModelScope.launch {
            val list = ArrayList<VMSpending>()
            val spendings = mainRepository.getSpendingsByCategoryId(categoryId, month, year)
            spendings.forEach { spending ->

                list.add(VMSpending(mainRepository.getCategoryById(spending.categoryId), spending))
            }
            liveVMSpendings.value = list
        }
    }

    fun removeVmSpending(spendingId: Long) {
        viewModelScope.launch {
            mainRepository.removeSpending(spendingId)
        }
    }
}

class VMSpending(val category: Category, val spending: Spending)