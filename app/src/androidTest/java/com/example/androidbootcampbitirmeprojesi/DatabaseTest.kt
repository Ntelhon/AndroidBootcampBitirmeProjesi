package com.example.androidbootcampbitirmeprojesi

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.androidbootcampbitirmeprojesi.database.Expense
import com.example.androidbootcampbitirmeprojesi.database.ExpenseDAO
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRoomDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var expenseDAO: ExpenseDAO
    private lateinit var database: ExpenseRoomDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(context, ExpenseRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        expenseDAO = database.expenseDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun addAnExpenseAndRead() {
        runBlocking {
            val expense = Expense()
            expenseDAO.insert(expense)
            val lastExpense = expenseDAO.getLastExpense()
            assertEquals(lastExpense?.currency, 0)
        }
    }

}