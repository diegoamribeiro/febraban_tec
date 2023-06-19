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
import com.cap.techsurvey.databinding.FragmentQuestionOneBinding
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.utils.viewBinding


class QuestionOneFragment : Fragment() {

    private val binding: FragmentQuestionOneBinding by viewBinding()
    private val args: QuestionOneFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("***AlreadyFragment", args.currentUser.toString())
        setListeners()

    }

    private fun setListeners() {
        binding.btNext.setOnClickListener {
            val action = QuestionOneFragmentDirections.actionNavQuestionOneToNavQuestionTwo(Survey(1, args.currentUser, questions = null, url = null))
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }


}