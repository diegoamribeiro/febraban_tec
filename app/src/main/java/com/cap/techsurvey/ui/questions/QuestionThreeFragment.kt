package com.cap.techsurvey.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionThreeBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionThreeFragment : Fragment() {

    private val binding: FragmentQuestionThreeBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}