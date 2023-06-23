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
import com.cap.techsurvey.databinding.FragmentQuestionFourBinding
import com.cap.techsurvey.entities.Option
import com.cap.techsurvey.entities.Question
import com.cap.techsurvey.entities.Survey
import com.cap.techsurvey.services.SurveyProvider
import com.cap.techsurvey.utils.Utils.gone
import com.cap.techsurvey.utils.Utils.invisible
import com.cap.techsurvey.utils.Utils.visible
import com.cap.techsurvey.utils.viewBinding


class QuestionFourFragment : Fragment() {

    private val binding: FragmentQuestionFourBinding by viewBinding()
    private val args: QuestionFourFragmentArgs by navArgs()
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
        binding.progressNext.gone()
        binding.btNext.invisible()
        Log.d("***Four", args.currentSurvey.toString())
        questions.clear()
        setListeners()
    }

    private fun setListeners(){

        binding.radioButtonOne.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ1", isChecked, 1)
            binding.btNext.visible()
        }
        binding.radioButtonTwo.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ2", isChecked, 0)
            binding.btNext.visible()
        }
        binding.radioButtonThree.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ3", isChecked, 0)
            binding.btNext.visible()
        }
        binding.radioButtonFour.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ4", isChecked, 0)
            binding.btNext.visible()
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
                val action = QuestionFourFragmentDirections.actionNavQuestionFourToNavQuestionFive(survey)
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

    private fun manageOptionSelection(optionId: String, isSelected: Boolean, score: Int) {
        val question = Question(
            id = "Q4",
            size = 1,
            weight = 1
        )
        val option = Option(id = optionId, score = score)
        val newQuestion = Question(id = question.id, option = option, weight =  question.weight, size = question.size, score = option.score!!.toDouble())
        if (isSelected) {
            questions.add(newQuestion)
        }else{
            questions.remove(newQuestion)
        }
    }

}