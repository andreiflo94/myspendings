package com.heixss.myspendings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.heixss.myspendings.repository.MainRepository

class MonthlySpendingsViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val liveSpendings = mainRepository.liveSpendings()
}
