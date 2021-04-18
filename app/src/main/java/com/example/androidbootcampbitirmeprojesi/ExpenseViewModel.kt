package com.example.androidbootcampbitirmeprojesi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidbootcampbitirmeprojesi.database.Expense
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExpenseViewModel(private val repository: ExpenseRepository) :ViewModel() {
    val allExpenses :LiveData<List<Expense>> = repository.allExpenses

    fun insert(expense: Expense) = viewModelScope.launch{
        repository.insert(expense)
    }

}


class ExpenseViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}