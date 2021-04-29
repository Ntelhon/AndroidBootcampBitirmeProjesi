package com.example.androidbootcampbitirmeprojesi

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
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

fun convertExpenseToString(expenses: List<Expense>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("heyy")
        append(resources.getString(R.string.temporary_title))
        expenses.forEach {
            append("<br>")
            append("\t${convertTypeToString(it.type, resources)}")
            append("<br>")
            append(resources.getString(R.string.amount))
            append("\t${it.amount} ")
            append("\t${convertCurrencyToString(it.currency, resources)}")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}