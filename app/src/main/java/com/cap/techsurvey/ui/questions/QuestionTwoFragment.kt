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
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.viewBinding


class QuestionTwoFragment : Fragment() {

    private val binding: FragmentQuestionTwoBinding by viewBinding()
    private val args: QuestionTwoFragmentArgs by navArgs()
    private lateinit var survey: Survey
    private lateinit var question: Question
    private val questions = mutableListOf<Question>()
    private val provider = SurveyProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("***Two", args.currentSurvey.toString())
        question = Question(
            id = "Q2",
            text = "",
            weight = 5
        )
        questions.addAll(args.currentSurvey.questions!!)

        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            questions = questions,
            url = null
        )
        setListeners()
    }

    private fun setListeners() {

        binding.radioButton1.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ1", isChecked, 1)
        }
        binding.radioButton2.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ2", isChecked, 2)
        }
        binding.radioButton3.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ3", isChecked, 3)
        }
        binding.radioButton4.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ4", isChecked, 4)
        }
        binding.radioButton5.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ5", isChecked, 5)
        }
        binding.radioButton6.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("GE6", isChecked, 0)
        }
        binding.radioButton7.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("GE6", isChecked, 0)
        }

        binding.btNext.setOnClickListener {
            provider.update(survey).addOnSuccessListener {
                Log.d("***FirebaseTwo", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionTwoFragmentDirections.actionNavQuestionTwoToNavQuestionThree(survey)
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
        val option = Option(
            id = optionId,
            text = "",
            answer = null,
            isSelected = isSelected,
            score = score
        )
        if (isSelected) {
            question.option = option
            question.score = (score.toDouble() / question.weight!!)
            questions.add(question)
            survey = survey.copy(questions = questions)
        }
    }


}