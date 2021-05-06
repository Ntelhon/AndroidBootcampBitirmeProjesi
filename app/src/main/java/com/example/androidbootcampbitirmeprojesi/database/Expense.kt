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
    var amount: Int = 0,

    @ColumnInfo(name = "Currency")
    var currency: Int = 0,

    @ColumnInfo(name = "Type")
    var type: Int = 0,

    @ColumnInfo(name = "Comment")
    var comment: String = "No comment"

    //@ColumnInfo(name = "Date")
    //val date: Calendar = Calendar.getInstance()
)

@Entity(tableName = "only_six")
data class ApiData(
    @PrimaryKey(autoGenerate = true)
    val id :Long = 0L,

    @ColumnInfo(name = "Name")
    val name: String = "noName",

    @ColumnInfo(name = "value")
    var value :Float = -1F
)