package com.cap.techsurvey.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.databinding.FragmentAlreadyBinding
import com.cap.techsurvey.utils.viewBinding



class AlreadyFragment : Fragment() {

    private val binding: FragmentAlreadyBinding by viewBinding()
    private val args: AlreadyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("***AlreadyFragment", args.currentUser.toString())
    }


}