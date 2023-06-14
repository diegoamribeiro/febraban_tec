package com.cap.techsurvey.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btNext.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_security_to_nav_full_name)
        }

        binding.btRefuse.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

}