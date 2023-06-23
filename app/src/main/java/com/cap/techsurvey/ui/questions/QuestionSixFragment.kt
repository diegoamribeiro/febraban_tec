package com.cap.techsurvey.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentQuestionSixBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.Utils.gone
import com.cap.techsurvey.utils.Utils.invisible
import com.cap.techsurvey.utils.Utils.visible
import com.cap.techsurvey.utils.viewBinding


class QuestionSixFragment : Fragment() {

    private val binding: FragmentQuestionSixBinding by viewBinding()
    private val args: QuestionSixFragmentArgs by navArgs()
    private lateinit var survey: Survey
    private val questions = mutableListOf<Question>()
    private val provider = SurveyProvider()
    private var groupOne = false
    private var groupTwo = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressNext.gone()
        binding.btNext.invisible()
        questions.clear()
        Log.d("***Six", args.currentSurvey.toString())
        setListeners()
    }

    private fun setListeners(){
        // Question A
        binding.radioOptionOneA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ1", isChecked, 1)
            groupOne = true
            enableButton()
        }
        binding.radioOptionTwoA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ2", isChecked, 2)
            groupOne = true
            enableButton()
        }
        binding.radioOptionThreeA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ3", isChecked, 3)
            groupOne = true
            enableButton()
        }
        binding.radioOptionFourA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ4", isChecked, 4)
            groupOne = true
            enableButton()
        }
        binding.radioOptionFiveA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ5", isChecked, 5)
            groupOne = true
            enableButton()
        }
        binding.radioOptionSixA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ6", isChecked, 0)
            groupOne = true
            enableButton()
        }
        // Question B
        binding.radioOptionOneB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ1", isChecked, 1)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionTwoB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ2", isChecked, 2)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionThreeB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ3", isChecked, 3)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionFourB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ4", isChecked, 4)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionFiveB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ5", isChecked, 5)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionSixB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ6", isChecked, 0)
            groupTwo = true
            enableButton()
        }

        questions.addAll(args.currentSurvey.questions!!)
        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            questions = questions,
            url = null
        )

        binding.btNext.setOnClickListener {
            binding.progressNext.visible()
            binding.btNext.invisible()
            provider.update(survey).addOnSuccessListener {
                binding.progressNext.gone()
                binding.btNext.visible()
                Log.d("***FirebaseSix", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionSixFragmentDirections.actionNavQuestionSixToNavQuestionSeven(survey)
                NavHostFragment.findNavController(this).navigate(action)
            }.addOnFailureListener { e ->
                binding.progressNext.gone()
                binding.btNext.visible()
                Toast.makeText(requireContext(), "Falha ao salvar dados!", Toast.LENGTH_SHORT).show()
                Log.w("***Firebase", "Error updating document", e)
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        binding.ivReset.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack(R.id.nav_onboard, false)
        }
    }

    private fun manageOptionSelectionOne(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_1",
            size = 5,
            weight = 1
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun manageOptionSelectionTwo(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_2",
            size = 5,
            weight = 1
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun isGroupsChecked() : Boolean {
        return groupOne && groupTwo
    }

    private fun enableButton() {
        if (isGroupsChecked()){
            binding.btNext.visible()
        }
    }
}