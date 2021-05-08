package com.example.androidbootcampbitirmeprojesi.detailandinsert

import androidx.lifecycle.*
import com.example.androidbootcampbitirmeprojesi.databaseandapi.Expense
import com.example.androidbootcampbitirmeprojesi.databaseandapi.ExpenseDAO
import kotlinx.coroutines.launch

class DetailViewModel (
    private val expenseId: Long = 0L,
    expenseDao: ExpenseDAO) : ViewModel() {

    private val dao = expenseDao

    private val _expense = MutableLiveData<Expense>()
    val expense :LiveData<Expense>
        get() = _expense

    fun getExpense() {
        viewModelScope.launch {
            _expense.value = dao.getExpenseWithId(expenseId)
        }
    }

    fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            dao.deleteWithId(expenseId)
        }
    }

}

class DetailViewModelFactory(
    private val expenseId: Long,
    private val dao: ExpenseDAO) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(expenseId, dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}