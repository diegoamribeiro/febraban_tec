package com.cap.techsurvey.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentFullNameBinding
import com.cap.techsurvey.utils.Utils
import com.cap.techsurvey.utils.Utils.hideKeyboard
import com.cap.techsurvey.utils.viewBinding


class FullNameFragment : Fragment() {

    private val binding: FragmentFullNameBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){

        binding.etName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.ilName.hint = ""
            }
        }

        binding.btNext.setOnClickListener {

            if (Utils.isNameValid(binding.etName.text.toString())){
                val action = FullNameFragmentDirections.actionNavFullNameToNavCompany(binding.etName.text.toString())
                NavHostFragment.findNavController(this).navigate(action)
                binding.ilName.error = null
            }else{
                binding.ilName.error = "Nome inválido. Deve ser no formato 'Nome Sobrenome'"
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

}