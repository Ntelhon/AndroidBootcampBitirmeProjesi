package com.example.androidbootcampbitirmeprojesi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbootcampbitirmeprojesi.database.Expense

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    var data = listOf<Expense>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        val curr = ExpenseViewModel.selectedCurrency

        holder.expenseName.text = item.comment
        if (item.comment.length > 15 && item.comment.isEmpty()) {
            holder.expenseName.text = item.comment.substring(0,14) + "..."
        }
        if (item.comment.isEmpty()) {
            holder.expenseName.text = convertTypeToString(item.type, res)
        }

        if (ExpenseViewModel.tLtoDollar == -1f) holder.expenseAmountAndCurrency.text = placeDot(item.amount) +" "+ convertCurrencyToString(item.currency, res)
        if (ExpenseViewModel.tLtoDollar != -1f) {
            val i = convertCurrencyToOther(item.amount, item.currency, curr)
            if( i > 999999) {
                holder.expenseAmountAndCurrency.textSize = 13F
            }
            holder.expenseAmountAndCurrency.text = placeDot(i) +" "+ convertCurrencyToString(curr, res)
        }

        holder.expenseType.setImageResource(setImageWithType(item.type))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_recycler, parent, false)
        return ViewHolder(
            view
        )
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.expense_name)
        val expenseAmountAndCurrency: TextView = itemView.findViewById(R.id.expense_amount_and_currency)
        val expenseType: ImageView = itemView.findViewById(R.id.expense_type)
    }
}

