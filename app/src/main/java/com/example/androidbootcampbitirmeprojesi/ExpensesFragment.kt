package com.example.androidbootcampbitirmeprojesi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
        val prefs = this.activity?.getSharedPreferences("com.example.androidbootcampbitirmeprojesi", Context.MODE_PRIVATE)

        val app = requireNotNull(this.activity).application
        val context = context
        val dao = ExpenseRoomDatabase.getDatabase(app).expenseDAO()
        val res = resources
        val apiDataDao = ApiDataRoomDatabase.getDatabase(app).apiDataDao()
        val repository = ExpenseRepository(dao)
        val expenseViewModelFactory = ExpenseViewModelFactory(repository, apiDataDao, app)
        val expenseViewModel = ViewModelProvider(this, expenseViewModelFactory).get(ExpenseViewModel::class.java)

        binding.lifecycleOwner = this

        val adapter = ExpenseAdapter()
        binding.recyclerViewExpenses.adapter = adapter
        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.buttonProfile.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.textUserName.text = prefs?.getString("userName", "Yeni Kullanıcı")+" "+ convertGenderToAdj(prefs?.getInt("gender", 2))


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
            it?.let { Snackbar.make(requireView(),it, Snackbar.LENGTH_LONG).show() }
        })

        expenseViewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            if (it == CurrencyApiStatus.InsertOk) {
                val intent = Intent (activity, MainActivity::class.java)
                activity?.startActivity(intent)
            }
        })

        expenseViewModel.allExpenses.observe(viewLifecycleOwner, Observer {
            if (prefs != null) {
                if(!prefs.getBoolean("NowInOne", true)){
                    println("all expenses observer")
                    expenseViewModel.setCompanionsToApiData()

                    val sum = sumAllExpenses(it, selectedCurrency)
                    val curr = convertCurrencyToString(selectedCurrency, res)
                    binding.textViewTotalExpenseNumber.text = placeDot(sum) + " " + curr
                }
            }
        })

        expenseViewModel.selectedCurrency.observe(viewLifecycleOwner, Observer {
            if (prefs != null) {
                if (!prefs.getBoolean("NowInOne", true)) {
                    println("selected currency observer")
                    expenseViewModel.setCompanionsToApiData()

                    binding.textViewTotalExpenseNumber.text =
                        expenseViewModel.selectedCurrency.value?.let { it1 -> placeDot(sumAllExpenses(expenseViewModel.allExpenses.value, it1)) + " " + convertCurrencyToString(selectedCurrency, res) }
                    expenseViewModel.allExpenses.value?.let { it1 -> adapter.data = it1 }
                }
            }
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_expenseInsertFragment)
        }
    }

}