package com.cap.techsurvey.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentAlreadyBinding
import com.cap.techsurvey.utils.viewBinding



class AlreadyFragment : Fragment() {

    private val binding: FragmentAlreadyBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}