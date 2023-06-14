package com.cap.techsurvey.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentSecurityBinding
import com.cap.techsurvey.utils.viewBinding


class SecurityFragment : Fragment() {

    private val binding: FragmentSecurityBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}