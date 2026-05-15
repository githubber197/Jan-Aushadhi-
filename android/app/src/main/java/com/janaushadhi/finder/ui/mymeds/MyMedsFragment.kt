package com.janaushadhi.finder.ui.mymeds

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.janaushadhi.finder.databinding.FragmentMymedsBinding
import com.janaushadhi.finder.viewmodel.MyMedicinesViewModel
import java.text.SimpleDateFormat
import java.util.*

class MyMedsFragment : Fragment() {

    private var _binding: FragmentMymedsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyMedicinesViewModel by viewModels()
    private lateinit var adapter: MyMedsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentMymedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyMedsAdapter { med -> viewModel.deleteMed(med.id) }
        binding.rvMyMeds.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyMedsFragment.adapter
        }

        viewModel.myMeds.observe(viewLifecycleOwner) { meds ->
            adapter.submitList(meds)
            binding.emptyState.visibility = if (meds.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            msg ?: return@observe
            Snackbar.make(binding.root, msg, 2500).show()
            viewModel.clearMessage()
        }

        binding.fabAddMed.setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val v = LayoutInflater.from(requireContext())
            .inflate(com.janaushadhi.finder.R.layout.dialog_add_med, null)
        val nameEt = v.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.janaushadhi.finder.R.id.etMedName)
        val dateEt = v.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.janaushadhi.finder.R.id.etRefillDate)
        val qtyEt  = v.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.janaushadhi.finder.R.id.etQuantity)

        // Default refill date = 30 days from now
        val cal = Calendar.getInstance().also { it.add(Calendar.DAY_OF_YEAR, 30) }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateEt.setText(sdf.format(cal.time))

        dateEt.setOnClickListener {
            DatePickerDialog(requireContext(), { _, y, m, d ->
                dateEt.setText(String.format("%04d-%02d-%02d", y, m + 1, d))
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Medicine")
            .setView(v)
            .setPositiveButton("Save") { _, _ ->
                val name = nameEt.text.toString().trim()
                val date = dateEt.text.toString().trim()
                val qty  = qtyEt.text.toString().toIntOrNull() ?: 30
                viewModel.addMed(name, date, qty)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
