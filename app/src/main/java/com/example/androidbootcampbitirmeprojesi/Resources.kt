package com.example.androidbootcampbitirmeprojesi

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbootcampbitirmeprojesi.database.Expense

fun convertCurrencyToString(currency: Int, resources: Resources): String {
    var currencyString = resources.getString(R.string.currency_0)
    when (currency) {
        1 -> currencyString = resources.getString(R.string.currency_1)
        2 -> currencyString = resources.getString(R.string.currency_2)
        3 -> currencyString = resources.getString(R.string.currency_3)
    }
    return currencyString
}

fun convertTypeToString(type: Int, resources: Resources): String {
    var typeString = resources.getString(R.string.type_5)
    when (type) {
        0 -> typeString = resources.getString(R.string.type_0)
        1 -> typeString = resources.getString(R.string.type_1)
        2 -> typeString = resources.getString(R.string.type_2)
        3 -> typeString = resources.getString(R.string.type_3)
        4 -> typeString = resources.getString(R.string.type_4)
    }
    return typeString
}

fun setImageWithType(type :Int, resources: Resources): Int {
    var a = R.drawable.other
    if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
        a = when (type) {
            0 -> R.drawable.food
            1 -> R.drawable.clothes
            2 -> R.drawable.invoice
            3 -> R.drawable.rent
            4 -> R.drawable.education
            5 -> R.drawable.other
            else -> R.drawable.other
        }
    } else {
        a = when (type) {
            0 -> R.drawable.food_dark
            1 -> R.drawable.clothes_dark
            2 -> R.drawable.invoice_dark
            3 -> R.drawable.rent_dark
            4 -> R.drawable.education_dark
            5 -> R.drawable.other_dark
            else -> R.drawable.other_dark
        }
    }
    return a
}

fun placeDot(amount: Int): String {
    var answer = amount.toString()
    val i = amount.toString()
    answer = when(amount.toString().length){
        in 0..3 -> i
        4 -> i.substring(0,1)+"."+ i.substring(1)
        5 -> i.substring(0,2)+"."+ i.substring(2)
        6 -> i.substring(0,3)+"."+ i.substring(3)
        7 -> i.substring(0,1)+"."+ i.substring(1,4)+"."+i.substring(4,7)
        8 -> i.substring(0,2)+"."+ i.substring(2,5)+"."+i.substring(5,8)
        else -> i
    }
    return answer
}

fun sumAllExpenses(expenses: List<Expense>?, selectedCurrency :Int): Int {
    var sum = 0
    when {
        expenses.isNullOrEmpty() -> return sum
        selectedCurrency == 0 -> {
            expenses.forEach {
                when(it.currency) {
                    0 -> sum += it.amount
                    1 -> sum += (it.amount / ExpenseViewModel.tLtoDollar).toInt()
                    2 -> sum += (it.amount / ExpenseViewModel.tLtoEuro).toInt()
                    3 -> sum += (it.amount / ExpenseViewModel.tLtoSterling).toInt()
                }
            }
        }
        selectedCurrency == 1 -> {
            expenses.forEach {
                when(it.currency) {
                    0 -> sum += (it.amount * ExpenseViewModel.tLtoDollar).toInt()
                    1 -> sum += it.amount
                    2 -> sum += (it.amount / ExpenseViewModel.dollarToEuro).toInt()
                    3 -> sum += (it.amount / ExpenseViewModel.dollarToSterling).toInt()
                }
            }
        }
        selectedCurrency == 2 -> {
            expenses.forEach {
                when(it.currency) {
                    0 -> sum += (it.amount * ExpenseViewModel.tLtoEuro).toInt()
                    1 -> sum += (it.amount * ExpenseViewModel.dollarToEuro).toInt()
                    2 -> sum += it.amount
                    3 -> sum += (it.amount / ExpenseViewModel.euroToSterling).toInt()
                }
            }
        }
        selectedCurrency == 3 -> {
            expenses.forEach {
                when(it.currency) {
                    0 -> sum += (it.amount * ExpenseViewModel.tLtoSterling).toInt()
                    1 -> sum += (it.amount * ExpenseViewModel.dollarToSterling).toInt()
                    2 -> sum += (it.amount * ExpenseViewModel.euroToSterling).toInt()
                    3 -> sum += it.amount
                }
            }
        }
    }
    return sum
}

fun convertCurrencyToOther(amount :Int, a :Int, b :Int): Int {
    var answer = amount
    when (b){
        0 -> {
            when(a) {
                0 -> answer = amount
                1 -> answer = (amount / ExpenseViewModel.tLtoDollar).toInt()
                2 -> answer = (amount / ExpenseViewModel.tLtoEuro).toInt()
                3 -> answer = (amount / ExpenseViewModel.tLtoSterling).toInt()
            }
        }
        1 -> {
            when(a) {
                0 -> answer = (amount * ExpenseViewModel.tLtoDollar).toInt()
                1 -> answer = amount
                2 -> answer = (amount / ExpenseViewModel.dollarToEuro).toInt()
                3 -> answer = (amount / ExpenseViewModel.dollarToSterling).toInt()
            }
        }
        2 -> {
            when(a) {
                0 -> answer = (amount * ExpenseViewModel.tLtoEuro).toInt()
                1 -> answer = (amount * ExpenseViewModel.dollarToEuro).toInt()
                2 -> answer = amount
                3 -> answer = (amount / ExpenseViewModel.euroToSterling).toInt()
            }
        }
        3 -> {
            when(a) {
                0 -> answer = (amount * ExpenseViewModel.tLtoSterling).toInt()
                1 -> answer = (amount * ExpenseViewModel.dollarToSterling).toInt()
                2 -> answer = (amount * ExpenseViewModel.euroToSterling).toInt()
                3 -> answer = amount
            }
        }
    }
    return answer
}