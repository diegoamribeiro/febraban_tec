package com.cap.techsurvey.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentContactBinding
import com.cap.techsurvey.entities.User
import com.cap.techsurvey.utils.Utils
import com.cap.techsurvey.utils.Utils.isValidEmail
import com.cap.techsurvey.utils.Utils.isValidPhoneNumber
import com.cap.techsurvey.utils.Utils.putTelCelMask
import com.cap.techsurvey.utils.viewBinding

class ContactFragment : Fragment() {

    private val binding: FragmentContactBinding by viewBinding()
    private val args: ContactFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        //Log.d("***Args", args.currentUser.toString())
    }


    private fun setListeners() {

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.contains(" ")) {
                    binding.etEmail.setText(s.toString().replace(" ", ""))
                    binding.etEmail.setSelection(binding.etEmail.text!!.length)
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.btNext.setOnClickListener {
            if (isValid(binding.etEmail.text.toString(), binding.etPhone.text.toString())) {
                val user = User(
                    name = args.currentUser.name,
                    email = binding.etEmail.text.toString(),
                    company = args.currentUser.company,
                    role = args.currentUser.role,
                    phone = binding.etPhone.text.toString(),
                    area = args.currentUser.area
                )
                val action = ContactFragmentDirections.actionNavContactToAlreadyFragment(user)
                NavHostFragment.findNavController(this)
                    .navigate(action)

            }
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.ilEmail.hint = ""
            }
        }

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etPhone.removeTextChangedListener(this)
                binding.etPhone.setText(putTelCelMask(s.toString()))
                binding.etPhone.setSelection(binding.etPhone.text!!.length)
                binding.etPhone.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.ilPhone.hint = ""
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }


    private fun isValid(
        email: String,
        phone: String
    ): Boolean {

        binding.ilEmail.error = null
        binding.ilPhone.error = null

        if (email.isEmpty() || !isValidEmail(email)) {
            binding.ilEmail.error = getString(R.string.required_email) // update the error message to an appropriate one
            return false
        } else {
            binding.ilEmail.error = null
        }

        if (phone.isEmpty() || !isValidPhoneNumber(phone)) {
            binding.ilPhone.error = getString(R.string.required_phone) // update the error message to an appropriate one
            return false
        } else {
            binding.ilPhone.error = null
        }

        return true
    }


}