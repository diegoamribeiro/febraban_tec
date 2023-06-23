package com.cap.techsurvey.ui.questions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.cap.techsurvey.R
import com.cap.techsurvey.databinding.FragmentFinishBinding
import com.cap.techsurvey.utils.viewBinding
import com.google.android.material.textview.MaterialTextView


class FinishFragment : Fragment() {

    private val binding: FragmentFinishBinding by viewBinding()
    private val args: FinishFragmentArgs by navArgs()
    private val animationDuration = 3000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatsValues(args.currentSurvey.result!!)
        Log.d("***FinishUrl", args.currentSurvey.url.toString())
        Log.d("***FinishScore", args.currentSurvey.toString())
    }

    private fun setStatsValues(stat: Double) {
        animateText(binding.mtvStats, stat)
        setProgressValues(stat)
        setListeners()
    }

    private fun setListeners(){
        binding.btNext.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack(R.id.nav_onboard, false)
        }
    }

    private fun animateText(mtv: MaterialTextView, statValue: Double) {
        val animator = ValueAnimator.ofFloat(0f, statValue.toFloat())
        animator.addUpdateListener { animation ->
            val animatedFloat = animation.animatedValue as Float
            val animatedValue = if (animatedFloat == 10f) {
                animatedFloat.toInt().toString()
            } else {
                String.format("%.1f", animatedFloat)
            }
            mtv.text = getString(R.string.text_result_stats, animatedValue)
        }
        animator.duration = animationDuration
        animator.start()
    }

    private fun setProgressValues(statValue: Double) {
        val progressIndicator = binding.progressResult
        val progress = statValue.toInt() * 100
        val animator = ObjectAnimator.ofInt(progressIndicator, "progress", progress)
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }


}