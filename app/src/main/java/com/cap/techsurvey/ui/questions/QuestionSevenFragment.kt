package com.cap.techsurvey.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionSevenBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionSevenFragment : Fragment() {

    private val binding: FragmentQuestionSevenBinding by viewBinding()

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
        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }
}