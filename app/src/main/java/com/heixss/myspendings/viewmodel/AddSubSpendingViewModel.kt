package com.heixss.myspendings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.myspendings.database.Spending
import com.heixss.myspendings.repository.MainRepository
import kotlinx.coroutines.launch
import java.util.*

class AddSubSpendingViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var categoryId: Long = 0
    fun addSpending(categoryId: Long, description: String, amount: Double, timeStamp: Long) {
        viewModelScope.launch {
            val cal: Calendar = Calendar.getInstance()
            cal.timeInMillis = timeStamp
            mainRepository.insertSpending(
                Spending(
                    categoryId = categoryId,
                    description = description,
                    value = amount,
                    day = cal.get(Calendar.DAY_OF_MONTH),
                    month = cal.get(Calendar.MONTH),
                    year = cal.get(Calendar.YEAR)
                )
            )
        }
    }

}