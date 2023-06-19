package com.cap.techsurvey.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionFourBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionFourFragment : Fragment() {

    private val binding: FragmentQuestionFourBinding by viewBinding()
    private val args: QuestionFourFragmentArgs by navArgs()

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

        binding.btNext.setOnClickListener {
            val action = QuestionFourFragmentDirections.actionNavQuestionFourToNavQuestionFive(args.currentSurvey)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

}