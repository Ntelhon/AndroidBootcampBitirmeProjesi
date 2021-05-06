package com.example.androidbootcampbitirmeprojesi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.ExpenseViewModel.Companion.selectedCurrency
import com.example.androidbootcampbitirmeprojesi.database.*
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpensesBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ExpensesFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentExpensesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expenses, container, false)

        val app = requireNotNull(this.activity).application
        val dao = ExpenseRoomDatabase.getDatabase(app).expenseDAO()
        val res = resources
        val apiDataDao = ApiDataRoomDatabase.getDatabase(app).apiDataDao()
        val repository = ExpenseRepository(dao)
        val expenseViewModelFactory = ExpenseViewModelFactory(repository, apiDataDao,app)
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory).get(ExpenseViewModel::class.java)

        binding.lifecycleOwner = this

        val adapter = ExpenseAdapter()
        binding.recyclerViewExpenses.adapter = adapter
        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.buttonGroupQuery.check(when(ExpenseViewModel.lastCheckedQuery) {
            1 -> R.id.buttonQueryDollar
            2 -> R.id.buttonQueryEuro
            3 -> R.id.buttonQuerySterling
            else -> R.id.buttonQueryTL
        })

        binding.buttonQueryTL.setOnClickListener {
            selectedCurrency = 0
            expenseViewModel.selectCurrency(0)
            ExpenseViewModel.lastCheckedQuery = 0
        }
        binding.buttonQueryDollar.setOnClickListener {
            selectedCurrency = 1
            expenseViewModel.selectCurrency(1)
            ExpenseViewModel.lastCheckedQuery = 1
        }
        binding.buttonQueryEuro.setOnClickListener {
            selectedCurrency = 2
            expenseViewModel.selectCurrency(2)
            ExpenseViewModel.lastCheckedQuery = 2
        }
        binding.buttonQuerySterling.setOnClickListener {
            selectedCurrency = 3
            expenseViewModel.selectCurrency(3)
            ExpenseViewModel.lastCheckedQuery = 3
        }

        expenseViewModel.answerApi.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(requireView(),"Veriler Güncellenemedi.", Snackbar.LENGTH_LONG).show()
                //binding.textViewTotalExpense.text = "Hata"
            }
        })

        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            expenseViewModel.setCompanionsToApiData()
            val sum = sumAllExpenses(it, ExpenseViewModel.selectedCurrency)
            val curr = convertCurrencyToString(ExpenseViewModel.selectedCurrency, res)
            binding.textViewTotalExpense.text = placeDot(sum) +" "+ curr
        })

        expenseViewModel.selectedCurrency.observe(viewLifecycleOwner, Observer {
            expenseViewModel.setCompanionsToApiData()
            binding.textViewTotalExpense.text =
                expenseViewModel.selectedCurrency.value?.let { it1 -> placeDot(sumAllExpenses(expenseViewModel.allExpenses.value, it1)) +" "+ convertCurrencyToString(ExpenseViewModel.selectedCurrency, res) }
            expenseViewModel.allExpenses.value?.let { it1 -> adapter.data = it1 }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //view.findViewById<Button>(R.id.button_first).setOnClickListener {
        //    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}
        view.findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_expenseInsertFragment)
        }
    }
}