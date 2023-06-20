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
    private lateinit var question: Question
    private val provider = SurveyProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("***FragmentOne", args.currentSurvey.toString())
        question = Question(
            id = "Q1",
            text = "",
            weight = 3
        )

        survey = Survey(
            id = "",
            user = args.currentSurvey.user,
            questions = mutableListOf(question),
            url = null
        )

        val restoredOption1 = restoreOptionSelection("EQ1")
        val restoredOption2 = restoreOptionSelection("EQ2")
        val restoredOption3 = restoreOptionSelection("EQ3")
        if (restoredOption1 != null) {
            binding.radioButton1.isChecked = true
            manageOptionSelection("EQ1", true, restoredOption1.score!!)
        }
        if (restoredOption2 != null) {
            binding.radioButton2.isChecked = true
            manageOptionSelection("EQ2", true, restoredOption2.score!!)
        }
        if (restoredOption3 != null) {
            binding.radioButton3.isChecked = true
            manageOptionSelection("EQ3", true, restoredOption3.score!!)
        }
        setListeners()
    }

    private fun setListeners() {
        binding.radioButton1.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ1", isChecked, 3)
        }

        binding.radioButton2.setOnCheckedChangeListener { _, isChecked ->
            manageOptionSelection("EQ2", isChecked, 2)
        }

        binding.radioButton3.setOnCheckedChangeListener { buttonView, isChecked ->
            manageOptionSelection("EQ3", isChecked, 1)
        }

        binding.btNext.setOnClickListener {
            provider.update(survey).addOnSuccessListener { documentReference ->
                Log.d("***Firebase", "DocumentSnapshot written with ID: ${documentReference.toString()}")
                // Aqui você pode configurar o id da survey
                Log.d("***Survey", survey.toString())
                val action = QuestionOneFragmentDirections.actionNavQuestionOneToNavQuestionTwo(survey)
                NavHostFragment.findNavController(this).navigate(action)
            }.addOnFailureListener { e ->
                Log.w("***Firebase", "Error adding document", e)
            }
        }

        binding.btBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

    private fun saveOptionSelection(id: String, isSelected: Boolean, score: Int) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(id, isSelected)
            putInt("$id-score", score)
            apply()
        }
    }

    private fun restoreOptionSelection(id: String): Option? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
        val isSelected = sharedPref.getBoolean(id, false)
        if (isSelected) {
            val score = sharedPref.getInt("$id-score", 0)
            return Option(id, "", null, isSelected, score)
        }
        return null
    }

    private fun manageOptionSelection(id: String, isSelected: Boolean, score: Int) {
        saveOptionSelection(id, isSelected, score)
        if (isSelected) {
            val option = Option(id, "", null, true, score)
            question.options = option
            question.score = option.score!! / question.weight!!.toDouble()
        } else {
            question.options = null
            question.score = null
        }
    }
}


