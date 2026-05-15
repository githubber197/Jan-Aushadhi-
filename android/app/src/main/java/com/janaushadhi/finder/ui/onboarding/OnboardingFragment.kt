package com.janaushadhi.finder.ui.onboarding

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.janaushadhi.finder.R
import com.janaushadhi.finder.databinding.FragmentOnboardingBinding
import com.janaushadhi.finder.utils.Constants

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: SharedPreferences

    private val slides = listOf(
        Triple("🔍", "Search Any Medicine",
            "Type a branded drug name and instantly discover its cheaper generic equivalent — powered by GenAI."),
        Triple("💰", "Save Up to 90%",
            "Compare prices side-by-side and calculate how much you save monthly by switching to generics."),
        Triple("🗺️", "Find Your Nearest Kendra",
            "Locate Jan-Aushadhi Kendras on a live map using OpenStreetMap — get directions instantly.")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireContext().getSharedPreferences(Constants.PREFS_NAME, 0)

        val adapter = OnboardingPagerAdapter(slides)
        binding.viewPager.adapter = adapter

        binding.btnSkip.setOnClickListener { finishOnboarding() }
        binding.btnNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < slides.size - 1) {
                binding.viewPager.currentItem = current + 1
                if (current + 1 == slides.size - 1) binding.btnNext.text = "Get Started"
            } else {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        prefs.edit().putBoolean(Constants.KEY_ONBOARDED, true).apply()
        findNavController().navigate(R.id.action_onboarding_to_home)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
