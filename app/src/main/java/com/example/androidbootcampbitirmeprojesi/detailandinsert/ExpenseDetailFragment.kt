package com.example.androidbootcampbitirmeprojesi.detailandinsert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.R
import com.example.androidbootcampbitirmeprojesi.databaseandapi.ExpenseRoomDatabase
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpenseDetailBinding
import com.example.androidbootcampbitirmeprojesi.setImageWithType

class ExpenseDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding :FragmentExpenseDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_expense_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = ExpenseDetailFragmentArgs.fromBundle(requireArguments())
        val action = ExpenseDetailFragmentDirections.actionExpenseDetailFragmentToFirstFragment()

        val dao = ExpenseRoomDatabase.getDatabase(application).expenseDAO()
        val viewModelFactory = DetailViewModelFactory(arguments.expenseId, dao)
        val detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        detailViewModel.getExpense()

        detailViewModel.expense.observe(viewLifecycleOwner, Observer {
            binding.detailImageView.setImageResource(setImageWithType(it.type))
            println("set ımage worked for ExpenseId: ${arguments.expenseId}")

            binding.detailTextView.text = it.comment
            println("set text worked")
        })

        binding.buttonDeleteExpense.setOnClickListener {
            detailViewModel.deleteExpense(arguments.expenseId)
            this.findNavController().navigate(action)
        }

        binding.buttonComeBack.setOnClickListener {
            this.findNavController().navigate(action)
        }

        return binding.root
    }

}