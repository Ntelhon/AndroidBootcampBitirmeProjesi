package com.example.androidbootcampbitirmeprojesi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Expense::class), version = 1, exportSchema = false)
public abstract class ExpenseRoomDatabase :RoomDatabase() {

    abstract fun expenseDAO(): ExpenseDAO

    companion object {

        @Volatile
        private var INSTANCE :ExpenseRoomDatabase? = null

        fun getDatabase(context: Context) :ExpenseRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ExpenseRoomDatabase::class.java, "expense_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}