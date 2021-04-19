package com.example.androidbootcampbitirmeprojesi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,

    @ColumnInfo(name = "Amount")
    val amount: Long = 0L,

    @ColumnInfo(name = "Currency")
    val currency: Int = 0,

    @ColumnInfo(name = "Type")
    val type: Int = 0

    //@ColumnInfo(name = "Comment")
    //val comment: String = "No comment",

    //@ColumnInfo(name = "Date")
    //val date: Calendar = Calendar.getInstance()
)