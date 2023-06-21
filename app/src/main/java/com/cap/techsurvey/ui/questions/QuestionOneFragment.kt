package com.cap.techsurvey.ui.questions

import android.content.Context
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
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.viewBinding


class QuestionOneFragment : Fragment() {
    private val binding: FragmentQuestionOneBinding by viewBinding()
    private val args: QuestionOneFragmentArgs by navArgs()
    private lateinit var survey: Survey
    private val provider = SurveyProvider()
    private val questions = mutableListOf<Question>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questions.clear()
        setListeners()
    }

    private fun setListeners() {

        binding.radioButton1.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ1", isChecked, 3)
        }

        binding.radioButton2.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ2", isChecked, 2)
        }

        binding.radioButton3.setOnCheckedChangeListener { _, isChecked ->

            manageOptionSelection("EQ3", isChecked, 1)
        }

        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            questions = questions,
            url = null
        )

        binding.btNext.setOnClickListener {
            provider.update(survey).addOnSuccessListener {
                Log.d("***Firebase", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionOneFragmentDirections.actionNavQuestionOneToNavQuestionTwo(survey)
                NavHostFragment.findNavController(this).navigate(action)
            }.addOnFailureListener { e ->
                Log.w("***Firebase", "Error updating document", e)
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

    private fun manageOptionSelection(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q1",
            size = 3,
            weight = 2
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }
}
