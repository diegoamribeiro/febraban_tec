package com.cap.techsurvey.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentCompanyBinding
import com.cap.techsurvey.utils.viewBinding


class CompanyFragment : Fragment() {

    private val binding: FragmentCompanyBinding by viewBinding()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


}