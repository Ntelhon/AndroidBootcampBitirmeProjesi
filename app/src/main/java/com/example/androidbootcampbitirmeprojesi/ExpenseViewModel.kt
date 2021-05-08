package com.example.androidbootcampbitirmeprojesi

import android.app.Application
import androidx.lifecycle.*
import com.example.androidbootcampbitirmeprojesi.database.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

enum class CurrencyApiStatus { LOADING, InsertOk, ERROR, DONE }
private const val ApiKey = "eefc72509d50091ecc31"  //eefc72509d50091ecc31  ---  7593590112cc067660dc

class ExpenseViewModel(private val repository: ExpenseRepository,private val apiDataDAO: ApiDataDAO, app: Application) :ViewModel() {
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
        getCurrencyValues()
    }

    fun insert(expense: Expense) = viewModelScope.launch{
        repository.insert(expense)
    }

    private fun insertAllApiData() = viewModelScope.launch{
        if(apiDataDAO.getAll().isNullOrEmpty()){
            for (i in 0..6) {
                val apiData = ApiData()
                apiDataDAO.insert(apiData)
                if (i == 6) _apiStatus.value = CurrencyApiStatus.InsertOk
            }
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

                val a = apiDataDAO.getAll()
                tLtoDollar = a[0].value
                tLtoEuro = a[1].value
                tLtoSterling = a[2].value
                dollarToEuro = a[3].value
                dollarToSterling = a[4].value
                euroToSterling = a[5].value
        }
    }


    private fun getCurrencyValues() {
        viewModelScope.launch {
            _apiStatus.value = CurrencyApiStatus.LOADING
            for (i in 0..2) {
                if (i == 0) {
                    var a = apiDataDAO.getAll()
                    if(a.isEmpty()) {
                        insertAllApiData()
                    }
                    a = apiDataDAO.getAll()

                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties1("TRY_USD,TRY_EUR", "ultra", ApiKey)
                        a[0].value = listResult.TRY_USD.toFloat()
                        a[1].value = listResult.TRY_EUR.toFloat()
                        apiDataDAO.update(a[0])
                        apiDataDAO.update(a[1])
                        println("${a[0].value} --- ${a[1].value}")
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure one: ${e.message}"
                    }
                }

                if (i == 1) {
                    val a = apiDataDAO.getAll()

                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties2("TRY_GBP,USD_EUR", "ultra", ApiKey)
                        a[2].value = listResult.TRY_GBP.toFloat()
                        a[3].value = listResult.USD_EUR.toFloat()
                        apiDataDAO.update(a[2])
                        apiDataDAO.update(a[3])
                        println("${a[2].value} --- ${a[3].value}")
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure two: ${e.message}"
                    }
                }

                if (i ==2) {
                    val a = apiDataDAO.getAll()

                    try {
                        val listResult = CurrencyApi.retrofitService.getProperties3("USD_GBP,EUR_GBP", "ultra", ApiKey)
                        a[4].value = listResult.USD_GBP.toFloat()
                        a[5].value = listResult.EUR_GBP.toFloat()
                        _apiStatus.value = CurrencyApiStatus.DONE
                        apiDataDAO.update(a[4])
                        apiDataDAO.update(a[5])
                        println("${a[4].value} --- ${a[5].value}")
                    } catch (e: Exception) {
                        _apiStatus.value = CurrencyApiStatus.ERROR
                        _answerApi.value = "Failure three: ${e.message}"
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