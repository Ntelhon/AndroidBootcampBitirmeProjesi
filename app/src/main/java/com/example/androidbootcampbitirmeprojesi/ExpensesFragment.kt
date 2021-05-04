package com.example.androidbootcampbitirmeprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.database.ExpenseAdapter
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRepository
import com.example.androidbootcampbitirmeprojesi.database.ExpenseRoomDatabase
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpensesBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_expenses.*

class ExpensesFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentExpensesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expenses, container, false)

        val app = requireNotNull(this.activity).application
        val dao = ExpenseRoomDatabase.getDatabase(app).expenseDAO()
        val repository = ExpenseRepository(dao)
        val res = app.resources
        val expenseViewModelFactory = ExpenseViewModelFactory(repository, app)
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory).get(ExpenseViewModel::class.java)

        //binding.setLifecycleOwner(this)

        val adapter = ExpenseAdapter()
        binding.recyclerViewExpenses.adapter = adapter
        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.buttonQueryTL.setOnClickListener {
            expenseViewModel.selectCurrency(0)
            expenseViewModel.getCurrencyValues()
        }
        binding.buttonQueryDollar.setOnClickListener {
            expenseViewModel.selectCurrency(1)
        }
        binding.buttonQueryEuro.setOnClickListener {
            expenseViewModel.selectCurrency(2)
        }
        binding.buttonQuerySterling.setOnClickListener {
            expenseViewModel.selectCurrency(3)
        }

        expenseViewModel.example.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.textViewTotalExpense.text = it
            }
        })

        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            binding.textViewTotalExpense.text = sumAllExpenses(it, res).toString()
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