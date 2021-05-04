package com.example.androidbootcampbitirmeprojesi.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbootcampbitirmeprojesi.R
import com.example.androidbootcampbitirmeprojesi.convertCurrencyToString

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    var data = listOf<Expense>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.expenseName.text = item.comment
        if (item.comment.length > 15) {
            holder.expenseName.text = item.comment.substring(0,14) + "..."
        }
        if (item.amount.toString().length > 5) {
            holder.expenseAmountAndCurrency.textSize = 18F
        }
        holder.expenseAmountAndCurrency.text = item.amount.toString() +" "+ convertCurrencyToString(item.currency, res)
        holder.expenseType.setImageResource(when (item.type) {
            0 -> R.drawable.food
            1 -> R.drawable.clothes
            2 -> R.drawable.invoice
            3 -> R.drawable.rent
            4 -> R.drawable.education
            5 -> R.drawable.other
            else -> R.drawable.other
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_recycler, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.expense_name)
        val expenseAmountAndCurrency: TextView = itemView.findViewById(R.id.expense_amount_and_currency)
        val expenseType: ImageView = itemView.findViewById(R.id.expense_type)
    }
}

