package com.cap.techsurvey.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs

import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentCompanyBinding
import com.cap.techsurvey.entities.User
import com.cap.techsurvey.utils.viewBinding


class CompanyFragment : Fragment() {

    private val binding: FragmentCompanyBinding by viewBinding()
    private val args: CompanyFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        //Log.d("***Args - ", args.currentName)
    }

    private fun setListeners() {

        binding.btNext.setOnClickListener {
            if (isValid(binding.etCompany.text.toString(), binding.etRole.text.toString())) {
                val user = User(
                    name = args.currentName,
                    company = binding.etCompany.text.toString(),
                    role = binding.etRole.text.toString(),
                    area = binding.etArea.text.toString()
                )
                val action = CompanyFragmentDirections.actionNavCompanyToContactFragment(user)
                NavHostFragment.findNavController(this)
                    .navigate(action)

            }
        }

        binding.etCompany.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.ilCompany.hint = ""
            }
        }

        binding.etRole.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.ilRole.hint = ""
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }


    private fun isValid(
        company: String,
        role: String
    ): Boolean {

        binding.ilCompany.error = null
        binding.ilRole.error = null

        if (company.isEmpty()) {
            binding.ilCompany.error = getString(R.string.required_company)
            return false
        }else{
            binding.ilCompany.error = null
        }

        if (role.isEmpty()) {
            binding.ilRole.error = getString(R.string.required_role)
            return false
        }else{
            binding.ilRole.error = null
        }

        return true
    }


}