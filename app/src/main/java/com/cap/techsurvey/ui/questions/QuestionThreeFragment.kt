package com.cap.techsurvey.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionThreeBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionThreeFragment : Fragment() {

    private val args: QuestionThreeFragmentArgs by navArgs()

    private val binding: FragmentQuestionThreeBinding by viewBinding()

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

    private fun setListeners() {
        binding.btNext.setOnClickListener {
            val action = QuestionThreeFragmentDirections.actionNavQuestionThreeToNavQuestionFour(args.currentSurvey)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }


}