package com.example.androidbootcampbitirmeprojesi

import android.app.Application
import androidx.lifecycle.*
import com.example.androidbootcampbitirmeprojesi.database.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

enum class CurrencyApiStatus { LOADING, ERROR, DONE }

class ExpenseViewModel(private val repository: ExpenseRepository, val apiDataDAO: ApiDataDAO, app: Application) :ViewModel() {
    val allExpenses :LiveData<List<Expense>> = repository.allExpenses
    companion object {
        var tLtoDollar :Float = -1f
        var tLtoEuro :Float = -1f
        var tLtoSterling :Float = -1f
        var dollarToEuro :Float = -1f
        var dollarToSterling :Float = -1f
        var euroToSterling :Float = -1f
        var selectedCurrency :Int = 0
        var lastCheckedQuery :Int = 0
    }

    private val _selectedCurrency = MutableLiveData<Int>()
    val selectedCurrency :LiveData<Int>
        get() = _selectedCurrency

    private val _apiStatus = MutableLiveData<CurrencyApiStatus>()
    val apiStatus :LiveData<CurrencyApiStatus>
        get() = _apiStatus

    private val _answerApi = MutableLiveData<String>()
    val answerApi :LiveData<String>
        get() = _answerApi


    init {
        _selectedCurrency.value = 0
        getCurrencyValues("TRY_USD,TRY_EUR")
        getCurrencyValues("TRY_GBP,USD_EUR")
        getCurrencyValues("USD_GBP,EUR_GBP")
    }

    fun insert(expense: Expense) = viewModelScope.launch{
        repository.insert(expense)
    }

    fun insertAllApiData() = viewModelScope.launch{
        for (i in 0..5) {
            val newData = ApiData()
            apiDataDAO.insert(newData)
        }

    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun selectCurrency(currency: Int) {
        _selectedCurrency.value = currency
    }

    fun setCompanionsToApiData() {
        viewModelScope.launch {
            val a = apiDataDAO.getAll() ?: return@launch
            if(a.size < 6) {
                insertAllApiData()
            }
            ExpenseViewModel.tLtoDollar = a[0].value
            ExpenseViewModel.tLtoEuro = a[1].value
            ExpenseViewModel.tLtoSterling = a[2].value
            ExpenseViewModel.dollarToEuro = a[3].value
            ExpenseViewModel.dollarToSterling = a[4].value
            ExpenseViewModel.euroToSterling = a[5].value
        }
    }


    private fun getCurrencyValues(requestApi :String) {
        viewModelScope.launch {
            _apiStatus.value = CurrencyApiStatus.LOADING
            val a = apiDataDAO.getAll() ?: return@launch
            if(a.size < 6) {
                insertAllApiData()
            }
            when (requestApi) {
                "TRY_USD,TRY_EUR" -> {
                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties1(requestApi, "ultra", "7593590112cc067660dc")
                        //_answerApi.value = "$listResult"
                        a[0].value = listResult.TRY_USD.toFloat()
                        a[1].value = listResult.TRY_EUR.toFloat()
                        _apiStatus.value = CurrencyApiStatus.DONE
                        apiDataDAO.update(a[0])
                        apiDataDAO.update(a[1])
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure: ${e.message}"
                    }
                }
                "TRY_GBP,USD_EUR" -> {
                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties2(requestApi, "ultra", "7593590112cc067660dc")
                        //_answerApi.value = "$listResult"
                        a[2].value = listResult.TRY_GBP.toFloat()
                        a[3].value = listResult.USD_EUR.toFloat()
                        _apiStatus.value = CurrencyApiStatus.DONE
                        apiDataDAO.update(a[2])
                        apiDataDAO.update(a[3])
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure: ${e.message}"
                    }
                }
                "USD_GBP,EUR_GBP" -> {
                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties3(requestApi, "ultra", "7593590112cc067660dc")
                        //_answerApi.value = "$listResult"
                        a[4].value = listResult.USD_GBP.toFloat()
                        a[5].value = listResult.EUR_GBP.toFloat()
                        _apiStatus.value = CurrencyApiStatus.DONE
                        apiDataDAO.update(a[4])
                        apiDataDAO.update(a[5])
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure: ${e.message}"
                    }
                }
            }

        }
    }

}


class ExpenseViewModelFactory(private val repository: ExpenseRepository, private val apiDataDAO: ApiDataDAO, private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository, apiDataDAO, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}