package com.example.androidbootcampbitirmeprojesi.detailandinsert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.ExpenseViewModel
import com.example.androidbootcampbitirmeprojesi.ExpenseViewModelFactory
import com.example.androidbootcampbitirmeprojesi.R
import com.example.androidbootcampbitirmeprojesi.databaseandapi.ApiDataRoomDatabase
import com.example.androidbootcampbitirmeprojesi.databaseandapi.Expense
import com.example.androidbootcampbitirmeprojesi.databaseandapi.ExpenseRepository
import com.example.androidbootcampbitirmeprojesi.databaseandapi.ExpenseRoomDatabase
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpenseInsertBinding
import com.example.androidbootcampbitirmeprojesi.setImageWithCurrency
import com.google.android.material.snackbar.Snackbar

class ExpenseInsertFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding : FragmentExpenseInsertBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_expense_insert, container, false)

        val app = requireNotNull(this.activity).application
        val dao = ExpenseRoomDatabase.getDatabase(app).expenseDAO()
        val repository = ExpenseRepository(dao)
        val apiDataDAO = ApiDataRoomDatabase.getDatabase(app).apiDataDao()
        val expenseViewModelFactory =
            ExpenseViewModelFactory(
                repository,
                apiDataDAO,
                app
            )
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory).get(
            ExpenseViewModel::class.java)

        //val items = listOf("Gıda", "Giyim", "Fatura", "Kira", "Eğitim", "Diğer")
        //val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        //binding.exposedDropdownMenu.setAdapter(adapter)

        binding.radioGroup.check(R.id.radioButtonTL)

        binding.imageViewTL.setImageResource(
            setImageWithCurrency(
                0
            )
        )
        binding.imageViewDollar.setImageResource(
            setImageWithCurrency(
                1
            )
        )
        binding.imageViewEuro.setImageResource(
            setImageWithCurrency(
                2
            )
        )
        binding.imageViewSterling.setImageResource(
            setImageWithCurrency(
                3
            )
        )

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
                currency = when(binding.radioGroup.checkedRadioButtonId) {
                                R.id.radioButtonTL -> 0
                                R.id.radioButtonDollar -> 1
                                R.id.radioButtonEuro -> 2
                                R.id.radioButtonSterling -> 3
                                else -> 0
                            }
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