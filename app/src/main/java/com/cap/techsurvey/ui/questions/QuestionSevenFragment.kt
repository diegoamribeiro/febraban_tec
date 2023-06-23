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
import com.cap.techsurvey.databinding.FragmentQuestionSevenBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.CircularDotsLoader
import com.cap.techsurvey.utils.Utils.gone
import com.cap.techsurvey.utils.Utils.invisible
import com.cap.techsurvey.utils.Utils.visible
import com.cap.techsurvey.utils.viewBinding


class QuestionSevenFragment : Fragment() {

    private val binding: FragmentQuestionSevenBinding by viewBinding()
    private val args: QuestionSevenFragmentArgs by navArgs()
    private lateinit var survey: Survey
    private val questions = mutableListOf<Question>()
    private val provider = SurveyProvider()
    private var groupOne = false
    private var groupTwo = false
    private var groupThree = false
    private var groupFour = false
    private var groupFive = false
    private var groupSix = false

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
        Log.d("***Seven", args.currentSurvey.toString())
        setListeners()
    }

    private fun setListeners(){
        // Question A
        binding.radioOptionOneA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_1", isChecked, 1)
            groupOne = true
            enableButton()
        }
        binding.radioOptionTwoA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_2", isChecked, 2)
            groupOne = true
            enableButton()
        }
        binding.radioOptionThreeA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_3", isChecked, 3)
            groupOne = true
            enableButton()
        }
        binding.radioOptionFourA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_4", isChecked, 4)
            groupOne = true
            enableButton()
        }
        binding.radioOptionFiveA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_5", isChecked, 5)
            groupOne = true
            enableButton()
        }
        binding.radioOptionSixA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_6", isChecked, 0)
            groupOne = true
            enableButton()
        }
        binding.radioOptionSevenA.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionOne("EQ_7", isChecked, 0)
            groupOne = true
            enableButton()
        }

        // Question B
        binding.radioOptionOneB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_1", isChecked, 1)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionTwoB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_2", isChecked, 2)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionThreeB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_3", isChecked, 3)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionFourB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_4", isChecked, 4)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionFiveB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_5", isChecked, 5)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionSixB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_6", isChecked, 0)
            groupTwo = true
            enableButton()
        }
        binding.radioOptionSevenB.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionTwo("EQ_7", isChecked, 0)
            groupTwo = true
            enableButton()
        }

        // Question C
        binding.radioOptionOneC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_1", isChecked, 1)
            groupThree = true
            enableButton()
        }
        binding.radioOptionTwoC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_2", isChecked, 2)
            groupThree = true
            enableButton()
        }
        binding.radioOptionThreeC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_3", isChecked, 3)
            groupThree = true
            enableButton()
        }
        binding.radioOptionFourC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_4", isChecked, 4)
            groupThree = true
            enableButton()
        }
        binding.radioOptionFiveC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_5", isChecked, 5)
            groupThree = true
            enableButton()
        }
        binding.radioOptionSixC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_6", isChecked, 0)
            groupThree = true
            enableButton()
        }
        binding.radioOptionSevenC.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionThree("EQ_7", isChecked, 0)
            groupThree = true
            enableButton()
        }

        // Question D
        binding.radioOptionOneD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_1", isChecked, 1)
            groupFour = true
            enableButton()
        }
        binding.radioOptionTwoD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_2", isChecked, 2)
            groupFour = true
            enableButton()
        }
        binding.radioOptionThreeD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_3", isChecked, 3)
            groupFour = true
            enableButton()
        }
        binding.radioOptionFourD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_4", isChecked, 4)
            groupFour = true
            enableButton()
        }
        binding.radioOptionFiveD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_5", isChecked, 5)
            groupFour = true
            enableButton()
        }
        binding.radioOptionSixD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_6", isChecked, 0)
            groupFour = true
            enableButton()
        }
        binding.radioOptionSevenD.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFour("EQ_7", isChecked, 0)
            groupFour = true
            enableButton()
        }

        // Question E
        binding.radioOptionOneE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_1", isChecked, 1)
            groupFive = true
            enableButton()
        }
        binding.radioOptionTwoE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_2", isChecked, 2)
            groupFive = true
            enableButton()
        }
        binding.radioOptionThreeE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_3", isChecked, 3)
            groupFive = true
            enableButton()
        }
        binding.radioOptionFourE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_4", isChecked, 4)
            groupFive = true
            enableButton()
        }
        binding.radioOptionFiveE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_5", isChecked, 5)
            groupFive = true
            enableButton()
        }
        binding.radioOptionSixE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_6", isChecked, 0)
            groupFive = true
            enableButton()
        }
        binding.radioOptionSevenE.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionFive("EQ_7", isChecked, 0)
            groupFive = true
            enableButton()
        }

        // Question F
        binding.radioOptionOneF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_1", isChecked, 1)
            groupSix = true
            enableButton()
        }
        binding.radioOptionTwoF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_2", isChecked, 2)
            groupSix = true
            enableButton()
        }
        binding.radioOptionThreeF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_3", isChecked, 3)
            groupSix = true
            enableButton()
        }
        binding.radioOptionFourF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_4", isChecked, 4)
            groupSix = true
            enableButton()
        }
        binding.radioOptionFiveF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_5", isChecked, 5)
            groupSix = true
            enableButton()
        }
        binding.radioOptionSixF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_6", isChecked, 0)
            groupSix = true
            enableButton()
        }
        binding.radioOptionSevenF.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelectionSix("EQ_7", isChecked, 0)
            groupSix = true
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
            // Calculate total score after all questions have been added
            provider.update(survey).addOnSuccessListener {
                binding.progressNext.gone()
                binding.btNext.visible()
                Log.d("***FirebaseSeven", "DocumentSnapshot updated with ID: ${survey.id}")
                val action = QuestionSevenFragmentDirections.actionNavQuestionSevenToNavReport(survey)
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



    private fun isGroupsChecked() : Boolean {
        return groupOne && groupTwo && groupThree && groupFour && groupFive && groupSix
    }

    private fun enableButton(){
        if (isGroupsChecked()){
            binding.btNext.visible()
        }
    }
}