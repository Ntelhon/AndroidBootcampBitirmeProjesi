package com.example.androidbootcampbitirmeprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpenseInsertBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.absoluteValue

class ExpenseInsertFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentExpenseInsertBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense_insert, container, false)

        val items = listOf("Gıda", "Giyim", "Fatura", "Kira", "Eğitim", "Diğer")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.exposedDropdownMenu.setAdapter(adapter)
        binding.exposedDropdownMenu.onItemSelectedListener = this

        val radioId = binding.radioButtonDollar.id

        var amountString:String
        var amount:Int?
        var currency:Int?
        val type:Int

        binding.buttonExpenseInsert.setOnClickListener {
            amountString = binding.editTextAmount.text.toString()
            if (amountString!=null && amountString!="") {
                amount = amountString.toInt()
                currency = binding.radioGroup.checkedRadioButtonId - radioId
                binding.temporaryText.text =
                    "Amt: " + amount + " Cur: " + currency + " Typ: "
            } else {
                Snackbar.make(
                    it,
                    "Lütfen bir miktar giriniz!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //val type: LiveData<Int> = parent?.getItemAtPosition(position) as LiveData<Int>
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        parent.setSelection(0)
    }
}