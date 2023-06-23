package com.cap.techsurvey.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.databinding.FragmentAlreadyBinding
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.ui.questions.QuestionOneFragmentDirections
import com.cap.techsurvey.utils.Utils.gone
import com.cap.techsurvey.utils.Utils.invisible
import com.cap.techsurvey.utils.Utils.visible
import com.cap.techsurvey.utils.viewBinding



class AlreadyFragment : Fragment() {
    private val binding: FragmentAlreadyBinding by viewBinding()
    private val args: AlreadyFragmentArgs by navArgs()
    private val provider = SurveyProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressNext.gone()

        val survey = Survey("", args.currentUser)
        binding.btStart.setOnClickListener {
            binding.progressNext.visible()
            binding.btStart.invisible()
            provider.create(survey).addOnSuccessListener { documentReference ->
                binding.btStart.visible()
                binding.progressNext.gone()
                Log.d("***Firebase", "DocumentSnapshot written with ID: ${documentReference.id}")
                Log.d("***FirebaseSurvey", survey.toString())
                val newSurvey = survey.copy(id = documentReference.id)
                val action = AlreadyFragmentDirections.actionNavAlreadyToNavQuestionOne(newSurvey)
                NavHostFragment.findNavController(this).navigate(action)
            }.addOnFailureListener { e ->
                binding.btStart.visible()
                binding.progressNext.gone()
                Log.w("***Firebase", "Error adding document", e)
            }
        }
    }
}