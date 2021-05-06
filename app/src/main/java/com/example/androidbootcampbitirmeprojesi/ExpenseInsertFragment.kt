package com.example.androidbootcampbitirmeprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.database.ApiDataRoomDatabase
import com.example.androidbootcampbitirmeprojesi.database.Expense
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRepository
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRoomDatabase
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpenseInsertBinding
import com.google.android.material.snackbar.Snackbar

class ExpenseInsertFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentExpenseInsertBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense_insert, container, false)

        val app = requireNotNull(this.activity).application
        val dao = ExpenseRoomDatabase.getDatabase(app).expenseDAO()
        val repository = ExpenseRepository(dao)
        val apiDataDAO = ApiDataRoomDatabase.getDatabase(app).apiDataDao()
        val expenseViewModelFactory = ExpenseViewModelFactory(repository, apiDataDAO, app)
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory).get(ExpenseViewModel::class.java)

        //val items = listOf("Gıda", "Giyim", "Fatura", "Kira", "Eğitim", "Diğer")
        //val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        //binding.exposedDropdownMenu.setAdapter(adapter)

        val radioId = binding.radioButtonDollar.id
        val radioIdType = binding.radioButtonType0.id

        var comment:String?
        var amountString:String
        var amount:Int?
        var currency:Int?
        var type:Int?

        binding.buttonExpenseInsert.setOnClickListener {
            amountString = binding.editTextAmount.text.toString()
            if (amountString!="" && amountString.length < 7) {
                comment = binding.editTextCommet.text.toString()
                amount = amountString.toInt()
                currency = binding.radioGroup.checkedRadioButtonId - radioId
                type = binding.radioGroupType.checkedRadioButtonId - radioIdType

                val expense = Expense()
                expense.comment = comment as String
                expense.amount = amount as Int
                expense.currency = currency as Int
                expense.type = type as Int
                expenseViewModel.insert(expense)

                Snackbar.make(
                        it,
                        "Harcamanız Başarıyla Eklenti",
                        Snackbar.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_expenseInsertFragment_to_FirstFragment)

            } else if (amountString.length > 6){
                Snackbar.make(
                    it,
                    "En fazla 6 karakter girebilirsiniz!",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                        it,
                        "Lütfen bir miktar giriniz!",
                        Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.temporaryClearAllButton.setOnClickListener {
            expenseViewModel.deleteAll()
        }

        return binding.root
    }
}