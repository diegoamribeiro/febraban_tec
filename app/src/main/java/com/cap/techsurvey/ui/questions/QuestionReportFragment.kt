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
import com.cap.techsurvey.databinding.FragmentQuestionReportBinding
import com.cap.techsurvey.utils.viewBinding


class QuestionReportFragment : Fragment() {

    private val binding: FragmentQuestionReportBinding by viewBinding()
    private val args: QuestionReportFragmentArgs by navArgs()
    private var totalScore = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Report", args.currentSurvey.questions.toString())
        setListeners()
        organizeData()
    }

    private fun setListeners(){

    }

    private fun organizeData() {
        val score1 = args.currentSurvey.questions?.get(0)?.score
        val score2 = args.currentSurvey.questions?.get(1)?.score
        val score3 = args.currentSurvey.questions?.get(2)?.score
        val score4 = args.currentSurvey.questions?.get(3)?.score
        val score5 = args.currentSurvey.questions?.get(4)?.score
        val score6 = args.currentSurvey.questions?.get(5)?.score
        val score7 = args.currentSurvey.questions?.get(6)?.score
        val score8 = args.currentSurvey.questions?.get(7)?.score
        val score9 = args.currentSurvey.questions?.get(8)?.score
        val score10 = args.currentSurvey.questions?.get(9)?.score
        val score11 = args.currentSurvey.questions?.get(10)?.score
        val score12 = args.currentSurvey.questions?.get(11)?.score
        val score13 = args.currentSurvey.questions?.get(12)?.score
        val score14 = args.currentSurvey.questions?.get(13)?.score

        totalScore = ((score1?.times(2)?.toDouble() ?: 0.0) +
                (score2?.times(2)?.toDouble() ?: 0.0) +
                (score3?.toDouble() ?: 0.0) +
                (score4?.toDouble() ?: 0.0) +
                (score5?.toDouble() ?: 0.0) +
                (score6?.times(2)?.toDouble() ?: 0.0) +
                (score7?.toDouble() ?: 0.0) +
                (score8?.toDouble() ?: 0.0) +
                ((score9?.toDouble() ?: 0.0) +
                        (score10?.toDouble() ?: 0.0) +
                        (score11?.toDouble() ?: 0.0) +
                        (score12?.toDouble() ?: 0.0) +
                        (score13?.toDouble() ?: 0.0) +
                        (score14?.toDouble() ?: 0.0)).times(3)) / 2.9

        Log.d("***TotalScore = ", totalScore.toString())


    }
}