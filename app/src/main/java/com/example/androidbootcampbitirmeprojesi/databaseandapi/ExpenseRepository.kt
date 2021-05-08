package com.example.androidbootcampbitirmeprojesi.databaseandapi

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ExpenseRepository(private val expenseDAO: ExpenseDAO) {

    val allExpenses: LiveData<List<Expense>> = expenseDAO.getExpensesWithId()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(expense: Expense){
        expenseDAO.insert(expense)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getLastExpense(){
        expenseDAO.getLastExpense()
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        expenseDAO.deleteAll()
    }
}