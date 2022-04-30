package com.heixss.myspendings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.myspendings.database.Category
import com.heixss.myspendings.database.Spending
import com.heixss.myspendings.repository.MainRepository
import kotlinx.coroutines.launch
import java.util.*

class AddSpendingViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun addSpending(category: String, description: String, amount: Double, timeStamp: Long) {
        viewModelScope.launch {
            val cal: Calendar = Calendar.getInstance()
            cal.timeInMillis = timeStamp

            mainRepository.getCategory(category)?.let { dbCategory ->
                mainRepository.insertSpending(
                    Spending(
                        categoryId = dbCategory.id,
                        description = description,
                        value = amount,
                        day = cal.get(Calendar.DAY_OF_MONTH),
                        month = cal.get(Calendar.MONTH),
                        year = cal.get(Calendar.YEAR)
                    )
                )
            } ?: run {
                mainRepository.insertCategory(Category(name = category))
                addSpending(category, description, amount, timeStamp)
            }

        }
    }
}
