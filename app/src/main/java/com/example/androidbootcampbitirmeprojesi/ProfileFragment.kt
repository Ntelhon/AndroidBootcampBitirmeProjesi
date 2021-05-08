package com.example.androidbootcampbitirmeprojesi

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.androidbootcampbitirmeprojesi.databinding.FragmentSecondBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding :FragmentSecondBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        val prefs = this.activity?.getSharedPreferences("com.example.androidbootcampbitirmeprojesi", Context.MODE_PRIVATE)

        binding.buttonSaveProfile.setOnClickListener {
            var userName = "Yeni Kullanıcı"
            if (!binding.editTextName.text.isNullOrEmpty())   userName = binding.editTextName.text.toString()

            val gender = when(binding.radioGroupGender.checkedRadioButtonId) {
            R.id.radioButtonGender0 -> 0
            R.id.radioButtonGender1 -> 1
            R.id.radioButtonGender2 -> 2
            else -> 2 }

            prefs?.edit()?.putString("userName", userName)?.apply()
            prefs?.edit()?.putInt("gender", gender)?.apply()

            val action = ProfileFragmentDirections.actionSecondFragmentToFirstFragment()
            findNavController().navigate(action)
        }


        return binding.root
    }

}