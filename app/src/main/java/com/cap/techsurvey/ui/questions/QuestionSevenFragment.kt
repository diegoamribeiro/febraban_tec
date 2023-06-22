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
import com.cap.techsurvey.databinding.FragmentQuestionSevenBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.viewBinding


class QuestionSevenFragment : Fragment() {

    private val binding: FragmentQuestionSevenBinding by viewBinding()
    private val args: QuestionSevenFragmentArgs by navArgs()
    private lateinit var survey: Survey
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
        questions.clear()
        Log.d("***Seven", args.currentSurvey.toString())
        setListeners()
    }

    private fun setListeners(){
        // Question A
        binding.radioOptionOneA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_7", isChecked, 0)
        }

        // Question B
        binding.radioOptionOneB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_7", isChecked, 0)
        }

        // Question C
        binding.radioOptionOneC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_7", isChecked, 0)
        }

        // Question D
        binding.radioOptionOneD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_7", isChecked, 0)
        }

        // Question E
        binding.radioOptionOneE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_7", isChecked, 0)
        }

        // Question F
        binding.radioOptionOneF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_1", isChecked, 1)
        }
        binding.radioOptionTwoF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_2", isChecked, 2)
        }
        binding.radioOptionThreeF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_3", isChecked, 3)
        }
        binding.radioOptionFourF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_4", isChecked, 4)
        }
        binding.radioOptionFiveF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_5", isChecked, 5)
        }
        binding.radioOptionSixF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_6", isChecked, 0)
        }
        binding.radioOptionSevenF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_7", isChecked, 0)
        }


        questions.addAll(args.currentSurvey.questions!!)
        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user,
            questions = questions,
            url = null
        )
        binding.btNext.setOnClickListener {
            provider.update(survey).addOnSuccessListener {
                Log.d("***FirebaseSeven", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionSevenFragmentDirections.actionNavQuestionSevenToNavReport(survey)
                NavHostFragment.findNavController(this).navigate(action)

            }.addOnFailureListener { e ->
                Log.w("***Firebase", "Error updating document", e)
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

    private fun manageOptionSelectionOne(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_1",
            size = 5,
            weight = 3
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
            weight = 3
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun manageOptionSelectionThree(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_3",
            size = 5,
            weight = 3
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun manageOptionSelectionFour(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_4",
            size = 5,
            weight = 3
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun manageOptionSelectionFive(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_5",
            size = 5,
            weight = 3
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = (option.score!!.toDouble() / question.size))
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

    private fun manageOptionSelectionSix(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q6_6",
            size = 5,
            weight = 3
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