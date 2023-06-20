package com.cap.techsurvey.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionTwoBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionTwoFragment : Fragment() {

    private val binding: FragmentQuestionTwoBinding by viewBinding()
    private val args: QuestionTwoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("*Two", args.currentSurvey.toString())
        setListeners()

    }

    private fun setListeners() {

        binding.btNext.setOnClickListener {
            val action = QuestionTwoFragmentDirections.actionNavQuestionTwoToNavQuestionThree(args.currentSurvey)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }


}