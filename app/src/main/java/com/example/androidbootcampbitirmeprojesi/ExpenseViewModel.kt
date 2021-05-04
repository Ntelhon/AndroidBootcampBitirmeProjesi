package com.example.androidbootcampbitirmeprojesi

import android.app.Application
import androidx.lifecycle.*
import com.example.androidbootcampbitirmeprojesi.database.CurrencyApi
import com.example.androidbootcampbitirmeprojesi.database.CurrencyProperty
import com.example.androidbootcampbitirmeprojesi.database.Expense
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.IllegalArgumentException
import java.util.*
import javax.security.auth.callback.Callback

class ExpenseViewModel(private val repository: ExpenseRepository, app: Application) :ViewModel() {
    val allExpenses :LiveData<List<Expense>> = repository.allExpenses

    private val _selectedCurrency = MutableLiveData<Int>()
    val selectedCurrency :LiveData<Int>
        get() = _selectedCurrency

    private val _tLtoDollar = MutableLiveData<Float>()
    val tLtoDollar :LiveData<Float>
        get() = _tLtoDollar

    private val _tLtoEuro = MutableLiveData<Float>()
    val tLtoEuro :LiveData<Float>
        get() = _tLtoEuro

    private val _tLtoSterling = MutableLiveData<Float>()
    val tLtoSterling :LiveData<Float>
        get() = _tLtoSterling

    private val _dollarToEuro = MutableLiveData<Float>()
    val dollarToEuro :LiveData<Float>
        get() = _dollarToEuro

    private val _dollarToSterling = MutableLiveData<Float>()
    val dollarToSterling :LiveData<Float>
        get() = _dollarToSterling

    private val _euroToSterling = MutableLiveData<Float>()
    val euroToSterling :LiveData<Float>
        get() = _euroToSterling


    init {
        _selectedCurrency.value = 0
    }

    fun insert(expense: Expense) = viewModelScope.launch{
        repository.insert(expense)
    }
    fun getLastExpense() = viewModelScope.launch{
        repository.getLastExpense()
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun selectCurrency(currency: Int) {
        _selectedCurrency.value = currency
    }

    private val _example = MutableLiveData<String>()
    val example :LiveData<String>
        get() = _example

    fun getCurrencyValues() {
        viewModelScope.launch {
            try {
                val listResult = CurrencyApi.retrofitService.getProperties()
                _example.value = "Success: $listResult Mars properties retrieved"
            } catch (e: Exception) {
                _example.value = "Failure: ${e.message}"
            }
        }
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