package com.example.androidbootcampbitirmeprojesi.databaseandapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 2, exportSchema = false)
abstract class ExpenseRoomDatabase :RoomDatabase() {

    abstract fun expenseDAO(): ExpenseDAO

    companion object {
        @Volatile
        private var INSTANCE :ExpenseRoomDatabase? = null

        fun getDatabase(context: Context) :ExpenseRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseRoomDatabase::class.java,
                        "expense_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Database(entities = [ApiData::class], version = 1, exportSchema = false)
abstract class ApiDataRoomDatabase :RoomDatabase() {

    abstract fun apiDataDao(): ApiDataDAO

    companion object {
        @Volatile
        private var INSTANCE :ApiDataRoomDatabase? = null

        fun getDatabase(context: Context) :ApiDataRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApiDataRoomDatabase::class.java,
                        "apiData_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}