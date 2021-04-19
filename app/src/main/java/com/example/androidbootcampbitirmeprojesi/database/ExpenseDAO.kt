package com.example.androidbootcampbitirmeprojesi.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDAO {
    @Query("Select * From expense_table Order By id DESC")
    fun getExpensesWithId(): LiveData<List<Expense>>

    @Query("Select * From expense_table Order By amount")
    fun getExpensesWithAmount(): LiveData<List<Expense>>

    //@Query("Select * From expense_table Order By date")
    //fun getExpensesWithDate(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expense: Expense)

    @Query("Select * From expense_table Order By Id Limit 1")
    fun getLastExpense(): Expense?

    @Query("Delete From expense_table Where id = :selectingId")
    fun deleteWithId(selectingId: Long)

    @Query("Delete From expense_table")
    fun deleteAll()

}