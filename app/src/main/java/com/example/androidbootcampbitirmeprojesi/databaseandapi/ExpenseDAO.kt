package com.example.androidbootcampbitirmeprojesi.databaseandapi

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDAO {
    @Query("Select * From expense_table Order By id DESC")
    fun getExpensesWithId(): LiveData<List<Expense>>

    @Query("Select * From expense_table Where id = :selectingId")
    suspend fun getExpenseWithId(selectingId: Long): Expense

    @Query("Select * From expense_table Order By amount")
    fun getExpensesWithAmount(): LiveData<List<Expense>>

    //@Query("Select * From expense_table Order By date")
    //fun getExpensesWithDate(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Query("Select * From expense_table Order By Id Limit 1")
    suspend fun getLastExpense(): Expense?

    @Query("Delete From expense_table Where id = :selectingId")
    suspend fun deleteWithId(selectingId: Long)

    @Query("Delete From expense_table")
    suspend fun deleteAll()

}

@Dao
interface ApiDataDAO {
    @Query("Select * From only_six Order By id DESC")
    suspend fun getAll() : List<ApiData>

    @Query("Select * From only_six Where id = :selectingId")
    suspend fun getId(selectingId: Long) :ApiData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(apiData: ApiData)

    @Update
    suspend fun update(apiData :ApiData)

}