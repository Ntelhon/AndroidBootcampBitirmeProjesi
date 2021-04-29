package com.example.androidbootcampbitirmeprojesi.database

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
}