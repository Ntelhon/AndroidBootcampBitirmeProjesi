package com.example.androidbootcampbitirmeprojesi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo
    val amount: Long = 0L,
    @ColumnInfo
    val currency: Int = 1,
    @ColumnInfo
    val type: Int = 1,
    @ColumnInfo
    val comment: String = "No comment",
    @ColumnInfo
    val date: Calendar = Calendar.getInstance()
)