package com.cap.techsurvey.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
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
        binding.tvName.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = view.text.toString()
                if (!Utils.isNameValid(name)) {
                    view.error = "Nome inválido. Deve ser no formato 'Nome Sobrenome'"
                } else {
                    // Substitua "action_to_next" pelo ID da ação de navegação correta do Navigation Graph
                    NavHostFragment.findNavController(this).navigate(R.id.action_nav_full_name_to_nav_company)
                }

                // Esconde o teclado
                hideKeyboard(view)
                true
            } else {
                false
            }
        }


        binding.tvName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.ilName.hint = ""
            } else {
                binding.ilName.hint = getString(R.string.hint_user_name)
            }
        }
    }





}