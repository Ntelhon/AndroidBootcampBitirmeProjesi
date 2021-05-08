package com.example.androidbootcampbitirmeprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentExpenseDetailBinding

class ExpenseDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding :FragmentExpenseDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense_detail, container, false)



        return binding.root
    }

}