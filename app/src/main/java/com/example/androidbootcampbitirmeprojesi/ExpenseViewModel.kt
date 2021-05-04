package com.example.androidbootcampbitirmeprojesi

import android.app.Application
import androidx.lifecycle.*
import com.example.androidbootcampbitirmeprojesi.database.Expense
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ExpenseViewModel(private val repository: ExpenseRepository, app: Application) :ViewModel() {
    val allExpenses :LiveData<List<Expense>> = repository.allExpenses

    fun insert(expense: Expense) = viewModelScope.launch{
        repository.insert(expense)
    }
    fun getLastExpense() = viewModelScope.launch{
        repository.getLastExpense()
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}


class ExpenseViewModelFactory(private val repository: ExpenseRepository, private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}