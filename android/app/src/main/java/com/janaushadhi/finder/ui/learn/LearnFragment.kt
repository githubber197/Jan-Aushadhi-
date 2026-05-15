package com.janaushadhi.finder.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janaushadhi.finder.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private val cards = listOf(
        Triple("🔬", "Same Active Ingredient",
            "Generic medicines contain the exact same active ingredient, dosage, and strength as branded drugs. CDSCO mandates bioequivalence — same rate and concentration in bloodstream."),
        Triple("💰", "Why Are Generics Cheaper?",
            "Branded companies spend billions on R&D and patents. Once patents expire (20 years), generic makers produce the same molecule without those costs — passing savings directly to you."),
        Triple("🏛️", "Government Quality Guarantee",
            "Every Jan-Aushadhi medicine is tested in NABL-accredited labs. BPPI monitors quality at every stage — from procurement to distribution."),
        Triple("🌍", "WHO Endorses Generics",
            "The WHO Essential Medicines List is entirely generic medicines. The UK's NHS, and countries worldwide, rely on generics as healthcare backbone — saving \$300B+ annually."),
        Triple("📋", "How to Read a Prescription",
            "When a doctor writes 'Crocin', the active ingredient is Paracetamol. Always ask your pharmacist for the generic salt name — Jan-Aushadhi Finder maps 500+ brands instantly."),
        Triple("🔔", "Never Miss a Refill",
            "Medication non-adherence is a major crisis. 50% of chronic patients stop medicines due to cost or forgetfulness. Our tracker cuts cost by 90% and sends reminders 2 days before you run out.")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LearnAdapter(cards)
        binding.rvLearnCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter  = adapter
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
