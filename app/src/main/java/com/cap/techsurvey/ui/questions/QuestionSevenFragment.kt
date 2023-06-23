package com.cap.techsurvey.ui.questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
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
    private var totalScore = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questions.clear()
        survey = Survey(
            id = args.currentSurvey.id,
            user = args.currentSurvey.user
        )

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

        binding.btNext.setOnClickListener {
            // Calculate total score after all questions have been added
            calculateScore()
            survey.questions = questions
            survey.result = totalScore
            survey.url = null
            provider.update(survey).addOnSuccessListener {
                Log.d("***FirebaseSeven", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionSevenFragmentDirections.actionNavQuestionSevenToNavReport(survey)
                NavHostFragment.findNavController(this).navigate(action)

            }.addOnFailureListener { e ->
                Log.w("***Firebase", "Error updating document", e)
            }
        }
    }

    private fun manageOptionSelectionOne(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q7_1",
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
            id = "Q7_2",
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
            id = "Q7_3",
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
            id = "Q7_4",
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
            id = "Q7_5",
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
            id = "Q7_6",
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

    private fun calculateScore() {
        val score1 = args.currentSurvey.questions?.get(0)?.score
        val score2 = args.currentSurvey.questions?.get(1)?.score
        val score3 = args.currentSurvey.questions?.get(2)?.score
        val score4 = args.currentSurvey.questions?.get(3)?.score
        val score5 = args.currentSurvey.questions?.get(4)?.score
        val score6 = args.currentSurvey.questions?.get(5)?.score
        val score7 = args.currentSurvey.questions?.get(6)?.score
        val score8 = args.currentSurvey.questions?.get(7)?.score
        val score9 = questions[0].score
        val score10 = questions[1].score
        val score11 = questions[2].score
        val score12 = questions[3].score
        val score13 = questions[4].score
        val score14 = questions[5].score

        totalScore = ((score1?.times(2) ?: 0.0) +
                (score2?.times(2) ?: 0.0) +
                (score3 ?: 0.0) +
                (score4 ?: 0.0) +
                (score5 ?: 0.0) +
                (score6?.times(2) ?: 0.0) +
                (score7 ?: 0.0) +
                (score8 ?: 0.0) +
                ((score9 ?: 0.0) +
                        (score10 ?: 0.0) +
                        (score11 ?: 0.0) +
                        (score12 ?: 0.0) +
                        (score13 ?: 0.0) +
                        (score14 ?: 0.0)).times(3)) / 2.9

        Log.d("***TotalScore = ", totalScore.toString())
    }
}