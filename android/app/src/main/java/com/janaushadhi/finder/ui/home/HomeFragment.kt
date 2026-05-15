package com.janaushadhi.finder.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.janaushadhi.finder.R
import com.janaushadhi.finder.databinding.FragmentHomeBinding
import com.janaushadhi.finder.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var popularAdapter: MedicineAdapter
    private lateinit var searchAdapter: MedicineAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupSearch()
        setupChips()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        popularAdapter = MedicineAdapter { medicine ->
            findNavController().navigate(R.id.action_home_to_detail,
                bundleOf("medicine_id" to medicine.id))
        }
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }

        searchAdapter = MedicineAdapter { medicine ->
            binding.searchResultsContainer.visibility = View.GONE
            binding.etSearch.clearFocus()
            findNavController().navigate(R.id.action_home_to_detail,
                bundleOf("medicine_id" to medicine.id))
        }
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun afterTextChanged(e: Editable?) {
                val q = e?.toString()?.trim() ?: ""
                if (q.length >= 2) {
                    viewModel.search(q)
                    binding.searchResultsContainer.visibility = View.VISIBLE
                } else {
                    viewModel.clearSearch()
                    binding.searchResultsContainer.visibility = View.GONE
                }
            }
        })
    }

    private fun setupChips() {
        // Guard: chips are added once; on back-navigation onViewCreated re-runs, avoid duplicates
        if (binding.chipGroup.childCount > 0) return
        val categories = listOf(
            "all" to "All", "pain" to "Pain/Fever", "diabetes" to "Diabetes",
            "bp" to "BP", "antibiotic" to "Antibiotics",
            "vitamin" to "Vitamins", "gastro" to "Gastro"
        )
        categories.forEach { (id, label) ->
            val chip = Chip(requireContext()).apply {
                text = label
                isCheckable = true
                isChecked = id == "all"
                setOnClickListener {
                    binding.chipGroup.clearCheck()
                    isChecked = true
                    viewModel.filterByCategory(id)
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun observeViewModel() {
        viewModel.popular.observe(viewLifecycleOwner) { list ->
            popularAdapter.submitList(list)
            binding.tvPopularEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            searchAdapter.submitList(results)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.backendOnline.observe(viewLifecycleOwner) { online ->
            binding.tvOfflineBanner.visibility = if (online) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
